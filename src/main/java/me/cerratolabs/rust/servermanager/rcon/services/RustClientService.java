package me.cerratolabs.rust.servermanager.rcon.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import me.cerratolabs.rust.servermanager.config.RustConfig;
import me.cerratolabs.rust.servermanager.rcon.listeners.ConsoleListener;
import me.cerratolabs.rust.servermanager.rcon.listeners.DeathEventListener;
import me.cerratolabs.rust.servermanager.rcon.listeners.PlayerListener;
import me.cerratolabs.rustrcon.client.RustClient;
import me.cerratolabs.rustrcon.events.messages.RustGenericMessage;
import me.nurio.events.handler.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.CompletableFuture;

@Service
public class RustClientService {

    private final Logger logger = LoggerFactory.getLogger(RustClientService.class);

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private RustConfig config;
    @Getter
    @Setter
    private RustClientBean rustClient;
    @Autowired
    private EventManagerService eventManager;
    @Autowired
    private ConsoleListener consoleListener;
    @Autowired
    private DeathEventListener deathEventListener;
    @Autowired
    private PlayerListener playerListener;
    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public void openClient() {
        eventManager.registerEvents(consoleListener);
        eventManager.registerEvents(deathEventListener);
        eventManager.registerEvents(playerListener);
        CompletableFuture.runAsync(this::verifyOpened);
    }

    public void start() {
        rustClient.startConnection(config.getAddress(), config.getPort(), config.getPassword());
    }

    @SneakyThrows
    public void verifyOpened() {
        while (true) {
            if (!isOpen()) {
                rustClient = context.getBean(RustClientBean.class, eventManager);
                start();
            }
            if (isOpen()) {
                logger.info("Connected!");
            }
            while (isOpen()) {
                Thread.sleep(2000);
            }
            if (!isOpen()) {
                logger.info("Connection lost, retrying!");
                Thread.sleep(2000);
            }
        }
    }

    private boolean isOpen() {
        return rustClient != null && rustClient.isOpen();
    }

    public void registerListener(EventListener listener) {
        eventManager.registerEvents(listener);
    }

    @SneakyThrows
    public void sendChatMessage(String message) {
        RustGenericMessage rustGenericMessage = new RustGenericMessage();
        rustGenericMessage.setType("Chat");
        rustGenericMessage.setIdentifier(0);
        rustGenericMessage.setStacktrace("");
        rustGenericMessage.setMessage("say " + message);
        this.sendMessage(rustGenericMessage);
    }

    @SneakyThrows
    public void sendMessage(String message) {
        RustGenericMessage rustGenericMessage = new RustGenericMessage();
        rustGenericMessage.setType("Generic");
        rustGenericMessage.setIdentifier(0);
        rustGenericMessage.setStacktrace("");
        rustGenericMessage.setMessage(message);
        this.sendMessage(rustGenericMessage);
    }

    @SneakyThrows
    public void sendMessage(RustGenericMessage message) {
        this.rustClient.sendMessage(message);
    }
}