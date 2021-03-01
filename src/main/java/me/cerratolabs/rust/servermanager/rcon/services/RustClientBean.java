package me.cerratolabs.rust.servermanager.rcon.services;

import me.cerratolabs.rustrcon.client.RustClient;
import me.nurio.events.EventManager;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RustClientBean extends RustClient {
    public RustClientBean(EventManager eventManager) {
        super(eventManager);
    }
}