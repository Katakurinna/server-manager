package me.cerratolabs.rust.servermanager.entity.services;

import me.cerratolabs.rust.servermanager.entity.entities.ServerEntity;
import me.cerratolabs.rust.servermanager.entity.jentity.ServerStatus;
import me.cerratolabs.rust.servermanager.entity.repository.ServerEntityRepository;
import me.cerratolabs.rust.servermanager.rest.exceptions.InvalidDataException;
import me.cerratolabs.rust.servermanager.rest.exceptions.ServerAlreadyExistException;
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

    public void save(ServerEntity server) {
        if (server == null) throw new InvalidDataException("You need a json body to be able to work");
        if (existServer(server.getAddress(), server.getPort()))
            throw new ServerAlreadyExistException("This server is currently on database");
        server.setStatus(ServerStatus.ACTIVE);
        repository.save(server);
    }

}