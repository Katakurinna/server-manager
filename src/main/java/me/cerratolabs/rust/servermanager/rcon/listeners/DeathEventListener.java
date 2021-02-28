package me.cerratolabs.rust.servermanager.rcon.listeners;

import me.cerratolabs.rust.servermanager.entity.services.DeathEventService;
import me.cerratolabs.rust.servermanager.rcon.services.RustClientService;
import me.cerratolabs.rustrcon.events.event.pvp.PlayerDeathByPlayerEvent;
import me.nurio.events.handler.EventHandler;
import me.nurio.events.handler.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeathEventListener implements EventListener {
    private final Logger logger = LoggerFactory.getLogger(DeathEventListener.class);

    @Autowired
    private DeathEventService deathEventService;

    @Autowired
    private RustClientService rustClient;


    @EventHandler
    public void killEvent(PlayerDeathByPlayerEvent event) {
        deathEventService.saveDeathEvent(event);
        String message = String.format("PvP: %s -> %s", event.getKiller().getUsername(), event.getPlayer().getUsername());
        logger.info(message);
        //rustClient.sendMessage("say " + message);
    }
}