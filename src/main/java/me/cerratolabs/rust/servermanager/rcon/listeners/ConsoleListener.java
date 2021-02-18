package me.cerratolabs.rust.servermanager.rcon.listeners;

import me.cerratolabs.rusrcon.events.messages.MessageReceiveEvent;
import me.cerratolabs.rust.servermanager.entity.services.MessageEntityService;
import me.nurio.events.handler.EventHandler;
import me.nurio.events.handler.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsoleListener implements EventListener {
    private final Logger logger = LoggerFactory.getLogger(ConsoleListener.class);

    @Autowired
    private MessageEntityService service;

    @EventHandler
    public void logConsole(MessageReceiveEvent event) {
        //service.save(event.getData());
        logger.info(event.getData().getMessage());
    }

}