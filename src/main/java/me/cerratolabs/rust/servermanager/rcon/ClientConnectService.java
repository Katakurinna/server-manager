package me.cerratolabs.rust.servermanager.rcon;

import me.cerratolabs.rust.servermanager.entity.services.ServerEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class ClientConnectService {

    @Autowired
    private ServerEntityService serverEntityService;

    @PostConstruct
    private void openConnections() {

    }

}
