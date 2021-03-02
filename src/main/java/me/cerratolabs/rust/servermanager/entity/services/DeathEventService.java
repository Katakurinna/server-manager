package me.cerratolabs.rust.servermanager.entity.services;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.SneakyThrows;
import me.cerratolabs.rust.servermanager.config.RustConfig;
import me.cerratolabs.rust.servermanager.entity.entities.DeathEventEntity;
import me.cerratolabs.rust.servermanager.entity.entities.PlayerEntity;
import me.cerratolabs.rust.servermanager.entity.entities.PlayerSeason;
import me.cerratolabs.rust.servermanager.entity.entities.ServerEntity;
import me.cerratolabs.rust.servermanager.entity.jentity.PlayerStats;
import me.cerratolabs.rust.servermanager.entity.jentity.Podium;
import me.cerratolabs.rust.servermanager.entity.jentity.podium.PodiumPlayer;
import me.cerratolabs.rust.servermanager.entity.repository.DeathEventRepository;
import me.cerratolabs.rustrcon.events.event.pvp.PlayerDeathByPlayerEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeathEventService {

    @Autowired
    private DeathEventRepository repository;

    @Autowired
    private PlayerEntityService playerEntityService;

    @Autowired
    private RustConfig config;

    @Autowired
    private WipeEntityService wipe;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PlayerSeasonService playerSeasonService;

    @Transactional
    public void saveDeathEvent(PlayerDeathByPlayerEvent event, ServerEntity server) {
        DeathEventEntity deathEventEntity = new DeathEventEntity();
        PlayerEntity killerEntity = playerEntityService.savePlayer(event.getKiller());
        PlayerSeason killerSeason = playerSeasonService.findMostRecentlyPlayerSeason(killerEntity);
        deathEventEntity.setKiller(killerSeason);
        PlayerEntity murderedEntity = playerEntityService.savePlayer(event.getKiller());
        PlayerSeason murderedSeason = playerSeasonService.findMostRecentlyPlayerSeason(murderedEntity);
        deathEventEntity.setMurdered(murderedSeason);
        deathEventEntity.setTimestamp(event.getTime());
        deathEventEntity.setReason(event.getReason());
        deathEventEntity.setWipe(wipe.findWipeByServer(server));
        deathEventEntity.setServer(server);
        repository.saveAndFlush(deathEventEntity);
    }

    public Integer getWipeKills(Integer seasonId, ServerEntity server) {
        Query query = entityManager.createNativeQuery("SELECT COUNT(killer_id) AS kills FROM death_event_entity WHERE wipe LIKE '" + wipe.findWipeByServer(server) + "' AND server = " + server.getId() + " AND killer_id = " + seasonId);
        Object kills = query.getSingleResult();
        return kills != null ? Integer.parseInt(kills.toString()) : 0;
    }

    public Integer getTotalKills(Integer seasonId) {
        Query query = entityManager.createNativeQuery("SELECT COUNT(killer_id) AS kills FROM death_event_entity WHERE killer_id = " + seasonId);
        Object kills = query.getSingleResult();
        return kills != null ? Integer.parseInt(kills.toString()) : 0;
    }

    public Integer getWipeDeaths(Integer seasonId, ServerEntity server) {
        Query query = entityManager.createNativeQuery("SELECT COUNT(murdered_id) AS kills FROM death_event_entity WHERE wipe LIKE '" + wipe.findWipeByServer(server) + "' AND server = " + server.getId() + " AND murdered_id = " + seasonId);
        Object kills = query.getSingleResult();
        return kills != null ? Integer.parseInt(kills.toString()) : 0;
    }

    public Integer getTotalDeaths(Integer seasonId) {
        Query query = entityManager.createNativeQuery("SELECT COUNT(murdered_id) AS kills FROM death_event_entity WHERE murdered_id = " + seasonId);
        Object kills = query.getSingleResult();
        return kills != null ? Integer.parseInt(kills.toString()) : 0;
    }

    public PlayerStats getPlayerStatsFromSteamID(Long steamID, ServerEntity server) {
        return getPlayerStats(playerEntityService.findBySteamId(steamID), server);
    }

    public PlayerStats getPlayerStatsFromDiscordID(String discordID, ServerEntity server) {
        PlayerEntity player = playerEntityService.findByDiscord(discordID);
        if (player == null || player.getId() == null) {
            throw new NullPointerException("Discord player doesn't exist");
        }
        return getPlayerStats(player, server);
    }

    public PlayerStats getPlayerStatsAndSaveDiscordID(Long steamID, String discordID, ServerEntity server) {
        return getPlayerStats(playerEntityService.addDiscordToEntity(steamID, discordID), server);
    }

    private PlayerStats getPlayerStats(PlayerEntity player, ServerEntity server) {
        PlayerStats stats = new PlayerStats();
        stats.setPlayer(player);
        PlayerSeason playerSeason = playerSeasonService.findMostRecentlyPlayerSeason(player);
        stats.setWipeKills(getWipeKills(playerSeason.getId(), server));
        stats.setWipeDeaths(getWipeDeaths(playerSeason.getId(), server));

        stats.setWipeKDR(calculateKDR(stats.getWipeKills(), stats.getWipeDeaths()));

        stats.setTotalKills(getTotalKills(playerSeason.getId()));
        stats.setTotalDeaths(getTotalDeaths(playerSeason.getId()));
        stats.setTotalKDR(calculateKDR(stats.getTotalKills(), stats.getTotalDeaths()));
        stats.setAvatar(getAvatar(player.getSteamId()));
        return stats;
    }

    @SneakyThrows
    private String getAvatar(Long steamID) {
        String url = "http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?key=" + config.getSteamKey() + "&steamids=" + steamID;
        RestTemplate restTemplate = new RestTemplate();
        ObjectNode result = restTemplate.getForObject(url, ObjectNode.class);
        return result
            .get("response")
            .get("players")
            .get(0)
            .get("avatarfull")
            .asText();
    }

    private Float calculateKDR(Integer kills, Integer deaths) {
        return (float) kills / (float) deaths;
    }


    public Integer countDeaths(Long steamID) {
        return repository.countDeaths(steamID);
    }
}