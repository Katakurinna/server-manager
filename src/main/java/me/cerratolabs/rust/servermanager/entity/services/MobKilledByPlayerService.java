package me.cerratolabs.rust.servermanager.entity.services;

import me.cerratolabs.rust.servermanager.entity.entities.MobKilledByPlayer;
import me.cerratolabs.rust.servermanager.entity.entities.ServerEntity;
import me.cerratolabs.rust.servermanager.entity.repository.MobKilledByPlayerRepository;
import me.cerratolabs.rustrcon.events.event.pve.MobKilledByPlayerEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MobKilledByPlayerService {

    @Autowired
    private MobKilledByPlayerRepository repository;

    @Autowired
    private PlayerSeasonService playerSeasonService;


    @Autowired
    private PlayerEntityService playerEntityService;

    @Autowired
    private WipeEntityService wipe;

    public void save(MobKilledByPlayerEvent event, ServerEntity server) {
        MobKilledByPlayer entity = new MobKilledByPlayer();
        entity.setPlayer(playerSeasonService.findMostRecentlyPlayerSeason(playerEntityService.savePlayer(event.getPlayer())));
        entity.setMob(event.getEntity().getEntityName());
        entity.setWipe(wipe.findWipeByServer(server));
        entity.setTimestamp(System.currentTimeMillis());
        entity.setServer(server);
        repository.save(entity);
    }

}
