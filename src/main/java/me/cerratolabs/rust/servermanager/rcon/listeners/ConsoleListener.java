package me.cerratolabs.rust.servermanager.rcon.listeners;

import me.cerratolabs.rust.servermanager.config.RustConfig;
import me.cerratolabs.rust.servermanager.entity.services.MessageEntityService;
import me.cerratolabs.rust.servermanager.rcon.services.RustClientBean;
import me.cerratolabs.rustrcon.events.messages.MessageReceiveEvent;
import me.nurio.events.handler.EventHandler;
import me.nurio.events.handler.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;


@Lazy
@Component
public class ConsoleListener implements EventListener {
    private final Logger logger = LoggerFactory.getLogger(ConsoleListener.class);

    @Autowired
    private MessageEntityService service;

    private RustClientBean rustClient;

    @Autowired
    private RustConfig config;

    @Autowired
    public ConsoleListener(RustClientBean rustClient) {
        this.rustClient = rustClient;
    }

    @EventHandler
    public void logConsole(MessageReceiveEvent event) {
        if (config.getSaveData()) {
            service.save(event.getData(), rustClient.getServer());
        }
        logger.info(event.getData().getMessage());
    }

    public ConsoleListener setRustClient(RustClientBean rustClient) {
        this.rustClient = rustClient;
        return this;
    }

}