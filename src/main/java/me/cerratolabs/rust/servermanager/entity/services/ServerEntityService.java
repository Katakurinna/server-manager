package me.cerratolabs.rust.servermanager.entity.services;

import lombok.SneakyThrows;
import me.cerratolabs.rust.servermanager.config.RustConfig;
import me.cerratolabs.rust.servermanager.encryption.SaltService;
import me.cerratolabs.rust.servermanager.entity.entities.ServerEntity;
import me.cerratolabs.rust.servermanager.entity.entities.WipeEntity;
import me.cerratolabs.rust.servermanager.entity.jentity.ServerStatus;
import me.cerratolabs.rust.servermanager.entity.repository.ServerEntityRepository;
import me.cerratolabs.rust.servermanager.rcon.ClientConnectService;
import me.cerratolabs.rust.servermanager.rest.exceptions.InvalidDataException;
import me.cerratolabs.rust.servermanager.rest.exceptions.ServerAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServerEntityService {

    @Autowired
    private ServerEntityRepository repository;

    @Autowired
    private RustConfig config;

    @Autowired
    private ClientConnectService clientConnectService;

    @Autowired
    private WipeEntityService wipeService;

    @Autowired
    private ApplicationContext context;

    public List<ServerEntity> findActiveServer() {
        return repository.findAllByStatus(ServerStatus.ACTIVE);
    }

    public boolean existServer(String address, Integer port) {
        return repository.existsServerEntityByAddressAndPort(address, port);
    }

    public void save(ServerEntity server) throws Exception {
        ServerEntity entity = null;
        WipeEntity wipeEntity = null;
        try {
            if (server == null) throw new InvalidDataException("You need a json body to be able to work");
            if (existServer(server.getAddress(), server.getPort())) throw new ServerAlreadyExistException("This server is currently on database");
            server.setStatus(ServerStatus.ACTIVE);
            String salt = SaltService.generateRandomSalt();
            server.setPassword(context.getBean(SaltService.class, salt).encrypt(server.getPassword()));
            server.setPasswordSalt(salt);
            entity = repository.save(server);
            wipeEntity = new WipeEntity();
            wipeEntity.setServer(server);
            wipeEntity.setStartDate(System.currentTimeMillis());
            wipeEntity = wipeService.save(wipeEntity);
            clientConnectService.addNewServer(entity);
        } catch (Exception e) {
            if (wipeEntity != null) wipeService.remove(wipeEntity);
            if (entity != null) remove(entity);
            throw e;
        }
    }

    @SneakyThrows
    public ServerEntity findActiveServerByIdWithSecuredPassword(Integer id) {
        return repository.findById(id).orElseThrow();
    }


    public void remove(ServerEntity serverEntity) {
        repository.delete(serverEntity);
    }
}