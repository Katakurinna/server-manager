package me.cerratolabs.rust.servermanager.entity.services;

import me.cerratolabs.rust.servermanager.entity.entities.*;
import me.cerratolabs.rust.servermanager.entity.repository.DeathEventRepository;
import me.cerratolabs.rustrcon.events.event.pvp.PlayerDeathEventsEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DeathEventService {

    @Autowired
    private DeathEventRepository repository;

    @Autowired
    private PlayerEntityService playerEntityService;

    @Autowired
    private WipeEntityService wipe;

    @Autowired
    private PlayerSeasonService playerSeasonService;

    @Transactional
    public void saveDeathEvent(PlayerDeathEventsEvent event, ServerEntity server) {
        DeathEventEntity deathEventEntity = new DeathEventEntity();
        PlayerEntity killerEntity = playerEntityService.savePlayer(event.getKiller());
        PlayerSeason killerSeason = playerSeasonService.findMostRecentlyPlayerSeason(killerEntity);
        deathEventEntity.setKiller(killerSeason);
        PlayerEntity murderedEntity = playerEntityService.savePlayer(event.getPlayer());
        PlayerSeason murderedSeason = playerSeasonService.findMostRecentlyPlayerSeason(murderedEntity);
        deathEventEntity.setMurdered(murderedSeason);
        deathEventEntity.setTimestamp(event.getTime());
        deathEventEntity.setReason(event.getReason());
        deathEventEntity.setHeadshot(event.isHeadshot());
        deathEventEntity.setDistance(event.getDistance());
        deathEventEntity.setWeapon(event.getWeapon());
        deathEventEntity.setWipe(wipe.findWipeByServer(server));
        deathEventEntity.setServer(server);
        repository.saveAndFlush(deathEventEntity);
    }

    public Integer countDeaths(Integer playerSeason) {
        return repository.countDeaths(playerSeason);
    }

    public List<DeathEventEntity> findAllByServerAndWipeOrderByTimestampDesc(ServerEntity server, WipeEntity wipe) {
        return repository.findAllByServerAndWipeOrderByTimestampDesc(server, wipe);
    }

    public Integer getWipeKills(PlayerSeason killer, WipeEntity wipe, ServerEntity server) {
        return repository.countAllByKillerAndWipeAndServer(killer, wipe, server);
    }

    public Integer getTotalKills(PlayerSeason killer, ServerEntity server) {
        return repository.countAllByKillerAndServer(killer, server);
    }

    public Integer getWipeDeaths(PlayerSeason killer, WipeEntity wipe, ServerEntity server) {
        return repository.countAllByMurderedAndWipeAndServer(killer, wipe, server);
    }

    public Integer getTotalDeaths(PlayerSeason murdered, ServerEntity server) {
        return repository.countAllByMurderedAndServer(murdered, server);
    }

    public Float obtainFurthestMurder(PlayerSeason playerSeason, ServerEntity server) {
        return repository.obtainFurthestMurder(playerSeason, server);
    }

    public Float obtainAverageHeadshot(PlayerSeason playerSeason, ServerEntity server) {
        return repository.obtainAverageHeadshot(playerSeason, server);
    }

    public String obtainMostUsedWeapon(PlayerSeason playerSeason, ServerEntity server) {
        List<String> strings = repository.obtainMostUsedWeapon(playerSeason, server);
        if (strings.isEmpty()) return null;
        return strings.get(0);
    }
}