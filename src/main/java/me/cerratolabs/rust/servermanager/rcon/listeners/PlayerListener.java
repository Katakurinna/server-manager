package me.cerratolabs.rust.servermanager.rcon.listeners;

import me.cerratolabs.rust.servermanager.entity.services.RustEntityService;
import me.cerratolabs.rust.servermanager.entity.services.TimeConnectedService;
import me.cerratolabs.rustrcon.entities.Player;
import me.cerratolabs.rustrcon.events.event.player.PlayerDisconnectEvent;
import me.cerratolabs.rustrcon.events.event.player.PlayerJoinEvent;
import me.nurio.events.handler.EventHandler;
import me.nurio.events.handler.EventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PlayerListener implements EventListener {

    @Autowired
    private RustEntityService service;

    @Autowired
    private TimeConnectedService connectedService;

    private Map<Player, Long> timeconnected = new HashMap<>();

    @EventHandler
    public void onPlayerLogin(PlayerJoinEvent event) {
        service.savePlayer(event);
        timeconnected.put(event.getPlayer(), System.currentTimeMillis());
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerDisconnectEvent event) {
        Long joinTime = timeconnected.containsKey(event.getPlayer()) ? timeconnected.get(event.getPlayer()) : System.currentTimeMillis();
        connectedService.save(event.getPlayer(), joinTime, System.currentTimeMillis());
    }
}