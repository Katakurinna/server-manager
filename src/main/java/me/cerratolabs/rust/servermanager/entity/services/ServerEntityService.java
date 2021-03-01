package me.cerratolabs.rust.servermanager.entity.services;

import me.cerratolabs.rust.servermanager.config.RustConfig;
import me.cerratolabs.rust.servermanager.encryption.EncryptionService;
import me.cerratolabs.rust.servermanager.entity.entities.ServerEntity;
import me.cerratolabs.rust.servermanager.entity.jentity.ServerStatus;
import me.cerratolabs.rust.servermanager.entity.repository.ServerEntityRepository;
import me.cerratolabs.rust.servermanager.rest.exceptions.InvalidDataException;
import me.cerratolabs.rust.servermanager.rest.exceptions.ServerAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@Service
public class ServerEntityService {

    @Autowired
    private ServerEntityRepository repository;

    @Autowired
    private EncryptionService aes128;

    @Autowired
    private RustConfig config;

    public List<ServerEntity> findActiveServer() {
        return repository.findAllByStatus(ServerStatus.ACTIVE);
    }

    public boolean existServer(String address, Integer port) {
        return repository.existsServerEntityByAddressAndPort(address, port);
    }

    public void save(ServerEntity server) throws BadPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        if (server == null) throw new InvalidDataException("You need a json body to be able to work");
        if (existServer(server.getAddress(), server.getPort())) throw new ServerAlreadyExistException("This server is currently on database");
        server.setStatus(ServerStatus.ACTIVE);
        String salt = aes128.generateRandomSalt();
        server.setPassword(aes128.encrypt(server.getPassword(), config.getPassword(), salt));
        server.setPasswordSalt(salt);
        repository.save(server);
    }

}