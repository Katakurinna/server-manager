package me.cerratolabs.rust.servermanager;

import io.graversen.fiber.event.listeners.IEventListener;
import io.graversen.rust.rcon.events.RustEvents;
import io.graversen.rust.rcon.events.implementation.ChatMessageEventParser;
import io.graversen.rust.rcon.events.types.player.ChatMessageEvent;
import io.graversen.rust.rcon.rustclient.RustClient;
import me.cerratolabs.rust.servermanager.services.RustClientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConnectionTests {
    @Autowired
    public RustClientService service;

    @Test
    public void connectionTest() {
        RustClient rustClient = service.getRustClient();
        Assertions.assertTrue(rustClient.isOpen());
    }

    @Test
    public void sendMessageTest() {
        RustClient rustClient = service.getRustClient();
        rustClient.send("say +1 Si este mensaje se ha enviado al servidor");
        rustClient.addEventHandling(RustEvents.CHAT_EVENT,
            new ChatMessageEventParser(),
            event -> Assertions.assertEquals("+1", event.getChatMessage()));
    }
}