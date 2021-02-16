package me.cerratolabs.rust.servermanager.services;

import io.graversen.fiber.event.listeners.IEventListener;
import io.graversen.rust.rcon.events.RustEvents;
import io.graversen.rust.rcon.events.implementation.ChatMessageEventParser;
import io.graversen.rust.rcon.events.implementation.PlayerDeathEventParser;
import io.graversen.rust.rcon.events.implementation.PlayerSuicideEventParser;
import io.graversen.rust.rcon.events.types.player.ChatMessageEvent;
import io.graversen.rust.rcon.rustclient.RustClient;
import lombok.Getter;
import lombok.Setter;
import me.cerratolabs.rust.servermanager.config.RustConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class RustClientService {
    @Autowired
    private RustConfig config;
    @Getter @Setter
    private RustClient rustClient;

    public void openClient() {
        if(rustClient != null && !rustClient.isOpen()) return;
        rustClient = RustClient.builder()
            .connectTo(
                config.getAddress(),
                config.getPassword(),
                config.getPort()
            ).build();
        rustClient.open();

        // DELETE ->
        rustClient.addEventHandling(RustEvents.CHAT_EVENT,
            new ChatMessageEventParser(),
            event -> System.out.println(event.getChatMessage()));
        rustClient.addEventHandling(RustEvents.PLAYER_SUICIDE_EVENT,
            new PlayerSuicideEventParser(),
            event -> System.out.println(event.getSuicideCause()));
        rustClient.addEventHandling(RustEvents.PLAYER_DEATH_EVENT,
            new PlayerDeathEventParser(),
            event -> System.out.println(event.getKiller()));
    }

    public RustClient getRustClient() {
        openClient();
        return rustClient;
    }
}