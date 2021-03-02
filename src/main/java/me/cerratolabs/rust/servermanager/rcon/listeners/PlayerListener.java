package me.cerratolabs.rust.servermanager.rcon.listeners;

import me.cerratolabs.rust.servermanager.config.RustConfig;
import me.cerratolabs.rust.servermanager.entity.services.PlayerEntityService;
import me.cerratolabs.rust.servermanager.entity.services.TimeConnectedService;
import me.cerratolabs.rust.servermanager.rcon.services.RustClientBean;
import me.cerratolabs.rustrcon.entities.Player;
import me.cerratolabs.rustrcon.events.event.player.PlayerDisconnectEvent;
import me.cerratolabs.rustrcon.events.event.player.PlayerJoinEvent;
import me.nurio.events.handler.EventHandler;
import me.nurio.events.handler.EventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Lazy
@Component
public class PlayerListener implements EventListener {

    private RustClientBean rustClient;

    @Autowired
    private PlayerEntityService service;

    @Autowired
    private TimeConnectedService connectedService;

    @Autowired
    private RustConfig config;

    private final Map<Player, Long> timeConnected = new HashMap<>();

    @Autowired
    public PlayerListener(RustClientBean rustClient) {
        this.rustClient = rustClient;
    }

    @EventHandler
    public void onPlayerLogin(PlayerJoinEvent event) {
        service.savePlayer(event);
        timeConnected.put(event.getPlayer(), System.currentTimeMillis());
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerDisconnectEvent event) {
        Long joinTime = timeConnected.containsKey(event.getPlayer()) ? timeConnected.get(event.getPlayer()) : System.currentTimeMillis();
        if (config.getSaveData()) {
            connectedService.save(event.getPlayer(), joinTime, System.currentTimeMillis(), rustClient.getServer());
        }
    }

    public PlayerListener setRustClient(RustClientBean rustClient) {
        this.rustClient = rustClient;
        return this;
    }
}