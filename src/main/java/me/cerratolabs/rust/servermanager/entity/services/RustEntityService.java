package me.cerratolabs.rust.servermanager.entity.services;

import me.cerratolabs.rust.servermanager.entity.entities.RustEntity;
import me.cerratolabs.rust.servermanager.entity.repository.RustEntityRepository;
import me.cerratolabs.rustrcon.entities.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class RustEntityService {
    @Autowired
    private RustEntityRepository repository;
    @Autowired
    private DeathEventService deathEventService;

    public RustEntity findById(String id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    public RustEntity savePlayer(Player player) {
        RustEntity byId = findById(player.getSteamID());
        if (byId != null && byId.getId() != null) return byId;
        RustEntity entity = new RustEntity();
        entity.setId(player.getSteamID());
        entity.setName(player.getUsername());
        repository.saveAndFlush(entity);
        return entity;
    }
}