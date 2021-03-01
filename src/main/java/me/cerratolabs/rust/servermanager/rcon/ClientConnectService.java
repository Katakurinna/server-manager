package me.cerratolabs.rust.servermanager.rcon;

import me.cerratolabs.rust.servermanager.config.RustConfig;
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

@Service
public class ClientConnectService {

    @Autowired
    private ServerEntityService serverEntityService;

    @Autowired
    private RustConfig config;

    @Autowired
    private ApplicationContext context;

    private Map<Integer, RustClientBean> serverList = new HashMap<>();

    @PostConstruct
    private void openConnections() {
        serverEntityService.findActiveServer().forEach(this::openConnection);
    }

    private void openConnection(ServerEntity s) {
        EventManager eventManager = context.getBean(EventManager.class, s.getId());
        eventManager.registerEvents(context.getBean(ConsoleListener.class, s.getId()));
        eventManager.registerEvents(context.getBean(DeathEventListener.class, s.getId()));
        eventManager.registerEvents(context.getBean(PlayerListener.class, s.getId()));
        RustClientBean rustClient = context.getBean(RustClientBean.class, eventManager);
        rustClient.startConnection(s.getAddress(), s.getPort(), s.getPassword());
    }

}
