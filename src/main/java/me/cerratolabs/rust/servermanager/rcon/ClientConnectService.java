package me.cerratolabs.rust.servermanager.rcon;

import lombok.SneakyThrows;
import me.cerratolabs.rust.servermanager.config.RustConfig;
import me.cerratolabs.rust.servermanager.encryption.SaltService;
import me.cerratolabs.rust.servermanager.entity.entities.ServerEntity;
import me.cerratolabs.rust.servermanager.entity.services.ServerEntityService;
import me.cerratolabs.rust.servermanager.rcon.listeners.ConsoleListener;
import me.cerratolabs.rust.servermanager.rcon.listeners.DeathEventListener;
import me.cerratolabs.rust.servermanager.rcon.listeners.PlayerListener;
import me.cerratolabs.rust.servermanager.rcon.services.RustClientBean;
import me.nurio.events.EventManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class ClientConnectService {

    @Autowired
    private ServerEntityService serverEntityService;

    @Autowired
    private RustConfig config;

    @Autowired
    private ApplicationContext context;

    //private final Map<Integer, RustClientBean> serverList = new HashMap<>();

    @PostConstruct
    private void openConnections() {
        ServerEntity serverEntity = serverEntityService.findActiveServerByIdWithSecuredPassword(config.getServerId());
        openConnection(serverEntity);
    }

    public void addNewServer(ServerEntity serverEntity) {
        // serverList.put(serverEntity.getId(), openConnection(serverEntity));
    }

    @SneakyThrows
    private RustClientBean openConnection(ServerEntity s) {
        EventManager eventManager = context.getBean(EventManager.class);
        RustClientBean rustClient = context.getBean(RustClientBean.class, eventManager);
        rustClient.setServer(s);
        eventManager.registerEvents(context.getBean(ConsoleListener.class, rustClient).setRustClient(rustClient));
        eventManager.registerEvents(context.getBean(DeathEventListener.class, rustClient).setRustClient(rustClient));
        eventManager.registerEvents(context.getBean(PlayerListener.class, rustClient).setRustClient(rustClient));
        rustClient.setSaltService(context.getBean(SaltService.class, s.getPasswordSalt()));
        rustClient.startConnection(s.getAddress(), s.getPort(), rustClient.getSaltService().decrypt(s.getPassword()));
        CompletableFuture.runAsync(rustClient::checkConnection);
        return rustClient;
    }

}