package me.cerratolabs.rust.servermanager.entity.services;

import me.cerratolabs.rust.servermanager.entity.entities.PlayerKilledByMob;
import me.cerratolabs.rust.servermanager.entity.entities.ServerEntity;
import me.cerratolabs.rust.servermanager.entity.repository.PlayerKilledByMobRepository;
import me.cerratolabs.rustrcon.entities.MobEntity;
import me.cerratolabs.rustrcon.entities.enums.DeathReason;
import me.cerratolabs.rustrcon.events.event.pve.PlayerDeathByMobEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerKilledByMobService {

    @Autowired
    private PlayerKilledByMobRepository repository;

    @Autowired
    private PlayerSeasonService playerSeasonService;


    @Autowired
    private PlayerEntityService playerEntityService;

    @Autowired
    private WipeEntityService wipe;

    public void save(PlayerDeathByMobEvent event, ServerEntity server) {
        PlayerKilledByMob entity = new PlayerKilledByMob();
        entity.setPlayer(playerSeasonService.findMostRecentlyPlayerSeason(playerEntityService.savePlayer(event.getPlayer())));
        entity.setReason(DeathReason.KILLED_BY_MOB);
        entity.setMob(((MobEntity) event.getEntity()).getEntityName());
        entity.setWipe(wipe.findWipeByServer(server));
        entity.setServer(server);
        repository.save(entity);
    }
}
