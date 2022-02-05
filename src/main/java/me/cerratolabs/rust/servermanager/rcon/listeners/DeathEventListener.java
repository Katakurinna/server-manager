package me.cerratolabs.rust.servermanager.rcon.listeners;

import me.cerratolabs.rust.servermanager.config.RustConfig;
import me.cerratolabs.rust.servermanager.entity.services.DeathEventService;
import me.cerratolabs.rust.servermanager.entity.services.MobKilledByPlayerService;
import me.cerratolabs.rust.servermanager.entity.services.PlayerKilledByMobService;
import me.cerratolabs.rust.servermanager.rcon.services.RustClientBean;
import me.cerratolabs.rustrcon.entities.MobEntity;
import me.cerratolabs.rustrcon.events.event.pve.MobKilledByPlayerEvent;
import me.cerratolabs.rustrcon.events.event.pve.PlayerDeathByMobEvent;
import me.cerratolabs.rustrcon.events.event.pvp.PlayerDeathByPlayerEvent;
import me.cerratolabs.rustrcon.events.event.pvp.PlayerDeathEventsEvent;
import me.nurio.events.handler.EventHandler;
import me.nurio.events.handler.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Lazy
@Component
public class DeathEventListener implements EventListener {
    private final Logger logger = LoggerFactory.getLogger(DeathEventListener.class);

    private RustClientBean rustClient;

    @Autowired
    private DeathEventService deathEventService;

    @Autowired
    private MobKilledByPlayerService mobKilledByPlayerService;
    @Autowired
    private PlayerKilledByMobService playerKilledByMobService;

    @Autowired
    private RustConfig config;

    @Autowired
    public DeathEventListener(RustClientBean rustClient) {
        this.rustClient = rustClient;
    }

    @EventHandler
    public void killEvent(PlayerDeathEventsEvent event) {
        if (config.getSaveData()) {
            deathEventService.saveDeathEvent(event, rustClient.getServer());
        }
    }

    @EventHandler
    public void mobDeathEvent(MobKilledByPlayerEvent event) {
        if (config.getSaveData()) {
            mobKilledByPlayerService.save(event, rustClient.getServer());
        }
    }

    @EventHandler
    public void mobDeathEvent(PlayerDeathByMobEvent event) {
        if (config.getSaveData()) {
            playerKilledByMobService.save(event, rustClient.getServer());
        }
    }


    public DeathEventListener setRustClient(RustClientBean rustClient) {
        this.rustClient = rustClient;
        return this;
    }

}