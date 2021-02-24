package me.cerratolabs.rust.servermanager.entity.services;

import me.cerratolabs.rust.servermanager.config.RustConfig;
import me.cerratolabs.rust.servermanager.entity.entities.DeathEventEntity;
import me.cerratolabs.rust.servermanager.entity.jentity.PlayerStats;
import me.cerratolabs.rust.servermanager.entity.jentity.Podium;
import me.cerratolabs.rust.servermanager.entity.jentity.podium.PodiumPlayer;
import me.cerratolabs.rust.servermanager.entity.repository.DeathEventRepository;
import me.cerratolabs.rustrcon.events.event.pvp.PlayerDeathByPlayerEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
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

    public List<DeathEventEntity> findAllByMurdered(String murderedID) {
        return null;
    }

    @Transactional
    public void saveDeathEvent(PlayerDeathByPlayerEvent event) {
        DeathEventEntity deathEventEntity = new DeathEventEntity();
        deathEventEntity.setId(UUID.randomUUID().toString());
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

    private PodiumPlayer parseToPodiumPlayer(Object[] obj) {
        PodiumPlayer player = new PodiumPlayer();
        player.setName(obj[2].toString());
        player.setSteamID(obj[0].toString());
        player.setKills(Integer.parseInt(obj[1].toString()));
        player.setPodiumPosition((int) Double.parseDouble(obj[3].toString()));
        player.setDeaths(repository.countDeaths(player.getSteamID()));
        return player;
    }

    public PlayerStats getPlayerStats(String steamID) {

    }

    public PlayerStats getPlayerStats(String discordID) {

    }
}