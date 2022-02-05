package me.cerratolabs.rust.servermanager.rcon.services;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import me.cerratolabs.rust.servermanager.encryption.SaltService;
import me.cerratolabs.rust.servermanager.entity.entities.ServerEntity;
import me.cerratolabs.rust.servermanager.entity.jentity.ServerStatus;
import me.cerratolabs.rustrcon.client.RustClient;
import me.nurio.events.EventManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Lazy
@Component
@Scope("prototype")
public class RustClientBean extends RustClient {

    private final Logger logger = LoggerFactory.getLogger(RustClientBean.class);

    @Getter
    @Setter
    private ServerEntity server;

    @Autowired
    private ApplicationContext context;

    @Getter
    @Setter
    private SaltService saltService;

    public RustClientBean(EventManager eventManager) {
        super(eventManager);
    }

    @SneakyThrows
    public void checkConnection() {
        while (server.getStatus().equals(ServerStatus.ACTIVE)) {
            if (isOpen()) {
                logger.info("Connected to {}:{}", server.getAddress(), server.getPort());
            }
            while (isOpen()) {
                Thread.sleep(500);
            }
            if (!isOpen()) {
                logger.error("Connection lost with {}:{}, retrying!", server.getAddress(), server.getPort());
                startConnection(server.getAddress(), server.getPort(), server.getPassword());
                Thread.sleep(1000);
            }
        }
    }
}