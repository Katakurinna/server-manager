package me.cerratolabs.rust.servermanager.entity.services;

import me.cerratolabs.rust.servermanager.entity.entities.ServerEntity;
import me.cerratolabs.rust.servermanager.entity.jentity.ServerStatus;
import me.cerratolabs.rust.servermanager.entity.repository.ServerEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServerEntityService {

    @Autowired
    private ServerEntityRepository repository;

    public List<ServerEntity> findActiveServer() {
        return repository.findAllByStatus(ServerStatus.ACTIVE);
    }

    public boolean existServer(String address, Integer port) {
        return repository.existsServerEntityByAddressAndPort(address, port);
    }

}
