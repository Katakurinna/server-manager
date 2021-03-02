package me.cerratolabs.rust.servermanager.entity.services;

import me.cerratolabs.rust.servermanager.config.RustConfig;
import me.cerratolabs.rust.servermanager.entity.entities.PlayerEntity;
import me.cerratolabs.rust.servermanager.entity.entities.PlayerSeason;
import me.cerratolabs.rust.servermanager.entity.entities.ServerEntity;
import me.cerratolabs.rust.servermanager.entity.jentity.Podium;
import me.cerratolabs.rust.servermanager.entity.jentity.podium.PodiumPlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PodiumService {

    @Autowired
    private RustConfig config;

    @Autowired
    private EntityManager em;

    @Autowired
    private WipeEntityService wipeService;

    @Autowired
    private ServerEntityService serverService;

    @Autowired
    private PlayerSeasonService playerSeasonService;

    @Autowired
    private DeathEventService deathService;

    public Podium getPodium(Integer serverId, Integer wipeId) {
        Query query = em.createNativeQuery("SELECT killer_id, kills, @row_number::=@row_number+1 AS row_number FROM ( SELECT killer_id, COUNT(killer_id) AS kills FROM death_event_entity WHERE wipe_id LIKE " + wipeId+ " AND server_id = " + serverId + " GROUP BY killer_id ORDER BY COUNT(killer_id) DESC LIMIT 5) AS d,(SELECT @row_number::=0) AS t");
        List<Object[]> list = query.getResultList();
        List<PodiumPlayer> collect = list.stream().map(this::parseToPodiumPlayer).collect(Collectors.toList());
        return new Podium(collect);
    }

    public Podium getPodium(Integer serverId) {
        return getPodium(serverId, wipeService.findWipeByServer(serverId).getId());
    }

    public Podium getPodium(ServerEntity server) {
        return getPodium(server.getId());
    }

    private PodiumPlayer parseToPodiumPlayer(Object[] obj) {
        PodiumPlayer player = new PodiumPlayer();
        PlayerSeason playerSeason = playerSeasonService.findById(Integer.parseInt(obj[2].toString()));
        PlayerEntity playerEntity = playerSeason.getPlayer();
        player.setName(playerEntity.getName());
        player.setSteamID(playerEntity.getSteamId());
        player.setKills(Integer.parseInt(obj[1].toString()));
        player.setPodiumPosition((int) Double.parseDouble(obj[3].toString()));
        player.setDeaths(deathService.countDeaths(player.getSteamID()));
        return player;
    }

}