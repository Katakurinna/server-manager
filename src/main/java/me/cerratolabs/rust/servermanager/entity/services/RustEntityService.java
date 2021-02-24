package me.cerratolabs.rust.servermanager.entity.services;

import me.cerratolabs.rust.servermanager.entity.entities.RustEntity;
import me.cerratolabs.rust.servermanager.entity.repository.RustEntityRepository;
import me.cerratolabs.rustrcon.entities.Player;
import me.cerratolabs.rustrcon.events.event.player.PlayerJoinEvent;
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
        return saveRustEntity(player);
    }

    public RustEntity savePlayer(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        return saveRustEntity(player);
    }

    private RustEntity saveRustEntity(Player player) {
        RustEntity byId = findById(player.getSteamID());

        if (byId != null && byId.getId() != null) {
            if (player.getUsername().equals(byId.getName())) {
                return byId;
            }
            byId.setName(byId.getName());
            repository.save(byId);
        }
        RustEntity entity = new RustEntity();
        entity.setId(player.getSteamID());
        entity.setName(player.getUsername());
        repository.saveAndFlush(entity);
        return entity;
    }

    public RustEntity findByDiscord(String discord) {
        return repository.findRustEntityByDiscord(discord);
    }

    public RustEntity addDiscordToEntity(String steamID, String discord) {
        RustEntity player = repository.findById(steamID).orElse(new RustEntity(steamID, "", discord));
        player.setDiscord(discord);
        return repository.saveAndFlush(player);
    }
}