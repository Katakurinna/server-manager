package me.cerratolabs.rust.servermanager.rcon.services;

import me.cerratolabs.rustrcon.client.RustClient;
import me.nurio.events.EventManager;
import org.springframework.stereotype.Service;

@Service
public class RustClientBean extends RustClient {
    public RustClientBean(EventManager eventManager) {
        super(eventManager);
    }
}