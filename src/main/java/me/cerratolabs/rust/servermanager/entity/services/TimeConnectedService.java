package me.cerratolabs.rust.servermanager.entity.services;

import me.cerratolabs.rust.servermanager.entity.entities.ServerEntity;
import me.cerratolabs.rust.servermanager.entity.entities.TimeConnected;
import me.cerratolabs.rust.servermanager.entity.repository.TimeConnectedRepository;
import me.cerratolabs.rustrcon.entities.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimeConnectedService {

    @Autowired
    private TimeConnectedRepository repository;

    @Autowired
    private PlayerEntityService playerEntityService;

    @Autowired
    private WipeEntityService wipeServer;

    public void save(Player player, Long join, Long left, ServerEntity serverEntity) {
        long l = System.currentTimeMillis();
        TimeConnected timeConnected = new TimeConnected();
        timeConnected.setPlayer(playerEntityService.savePlayer(player));
        timeConnected.setJoin(join);
        timeConnected.setLeft(left);
        timeConnected.setConnectedMillis(left - join);
        timeConnected.setWipe(wipeServer.findWipeByServer(serverEntity));
        timeConnected.setServer(serverEntity);
        repository.save(timeConnected);
    }
}