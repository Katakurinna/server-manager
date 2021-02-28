package me.cerratolabs.rust.servermanager.entity.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.SneakyThrows;
import me.cerratolabs.rust.servermanager.config.RustConfig;
import me.cerratolabs.rust.servermanager.entity.entities.DeathEventEntity;
import me.cerratolabs.rust.servermanager.entity.entities.PlayerEntity;
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
    private RustEntityService rustEntityService;

    @Autowired
    private RustConfig config;

    @Autowired
    private EntityManager entityManager;

    private ObjectMapper mapper = new ObjectMapper();

    public List<DeathEventEntity> findAllByMurdered(String murderedID) {
        return null;
    }

    @Transactional
    public void saveDeathEvent(PlayerDeathByPlayerEvent event) {
        DeathEventEntity deathEventEntity = new DeathEventEntity();
        deathEventEntity.setKiller(rustEntityService.savePlayer(event.getKiller()));
        deathEventEntity.setMurdered(rustEntityService.savePlayer(event.getPlayer()));
        deathEventEntity.setTimestamp(event.getTime());
        deathEventEntity.setReason(event.getReason());
        deathEventEntity.setWipeVersion(config.getWipeVersion());
        repository.saveAndFlush(deathEventEntity);
    }

    public Podium getPodium() {
        Query query = entityManager.createNativeQuery("SELECT killer_id, kills, name, @row_number::=@row_number+1 AS row_number FROM ( SELECT killer_id, COUNT(killer_id) AS kills FROM death_event_entity WHERE wipe_version LIKE '" + config.getWipeVersion() + "' GROUP BY killer_id ORDER BY COUNT(killer_id) DESC LIMIT 5) AS d,(SELECT @row_number::=0) AS t,(SELECT id, name FROM rust_entity) as e WHERE e.id = killer_id");
        List<Object[]> list = query.getResultList();
        List<PodiumPlayer> collect = list.stream().map(this::parseToPodiumPlayer).collect(Collectors.toList());
        return new Podium(collect);
    }

    public Integer getWipeKills(Long steamID) {
        Query query = entityManager.createNativeQuery("SELECT COUNT(killer_id) AS kills FROM death_event_entity WHERE wipe_version LIKE '" + config.getWipeVersion() + "' AND killer_id = " + steamID);
        Object kills = query.getSingleResult();
        return kills != null ? Integer.parseInt(kills.toString()) : 0;
    }

    public Integer getTotalKills(Long steamID) {
        Query query = entityManager.createNativeQuery("SELECT COUNT(killer_id) AS kills FROM death_event_entity WHERE killer_id = " + steamID);
        Object kills = query.getSingleResult();
        return kills != null ? Integer.parseInt(kills.toString()) : 0;
    }

    public Integer getWipeDeaths(Long steamID) {
        Query query = entityManager.createNativeQuery("SELECT COUNT(murdered_id) AS kills FROM death_event_entity WHERE wipe_version LIKE '" + config.getWipeVersion() + "' AND murdered_id = " + steamID);
        Object kills = query.getSingleResult();
        return kills != null ? Integer.parseInt(kills.toString()) : 0;
    }

    public Integer getTotalDeaths(Long steamID) {
        Query query = entityManager.createNativeQuery("SELECT COUNT(murdered_id) AS kills FROM death_event_entity WHERE murdered_id = " + steamID);
        Object kills = query.getSingleResult();
        return kills != null ? Integer.parseInt(kills.toString()) : 0;
    }

    private PodiumPlayer parseToPodiumPlayer(Object[] obj) {
        PodiumPlayer player = new PodiumPlayer();
        player.setName(obj[2].toString());
        player.setSteamID(obj[0].toString());
        player.setKills(Integer.parseInt(obj[1].toString()));
        player.setPodiumPosition((int) Double.parseDouble(obj[3].toString()));
        player.setDeaths(repository.countDeaths(player.getSteamID()));
        return player;
    }

    public PlayerStats getPlayerStatsFromSteamID(Long steamID) {
        return getPlayerStats(rustEntityService.findBySteamId(steamID));
    }

    public PlayerStats getPlayerStatsFromDiscordID(String discordID) {
        PlayerEntity player = rustEntityService.findByDiscord(discordID);
        if (player == null || player.getId() == null) {
            throw new NullPointerException("Discord player doesn't exist");
        }
        return getPlayerStats(player);
    }

    public PlayerStats getPlayerStatsAndSaveDiscordID(Long steamID, String discordID) {
        return getPlayerStats(rustEntityService.addDiscordToEntity(steamID, discordID));
    }

    private PlayerStats getPlayerStats(PlayerEntity player) {
        PlayerStats stats = new PlayerStats();
        stats.setPlayer(player);
        stats.setWipeKills(getWipeKills(player.getSteamId()));
        stats.setWipeDeaths(getWipeDeaths(player.getSteamId()));

        stats.setWipeKDR(calculateKDR(stats.getWipeKills(), stats.getWipeDeaths()));

        stats.setTotalKills(getTotalKills(player.getSteamId()));
        stats.setTotalDeaths(getTotalDeaths(player.getSteamId()));
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


}