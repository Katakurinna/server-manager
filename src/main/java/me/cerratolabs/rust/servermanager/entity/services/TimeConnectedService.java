package me.cerratolabs.rust.servermanager.entity.services;

import me.cerratolabs.rust.servermanager.config.RustConfig;
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
    private RustEntityService rustEntityService;

    @Autowired
    private RustConfig config;

    public void save(Player player, Long join, Long left) {
        long l = System.currentTimeMillis();
        TimeConnected timeConnected = new TimeConnected();
        timeConnected.setPlayer(rustEntityService.savePlayer(player));
        timeConnected.setJoin(join);
        timeConnected.setLeft(left);
        timeConnected.setConnectedMillis(left - join);
        timeConnected.setWipeVersion(config.getWipeVersion());
        repository.save(timeConnected);
    }
}