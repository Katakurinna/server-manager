package me.cerratolabs.rust.servermanager.entity.services;

import me.cerratolabs.rust.servermanager.entity.entities.PlayerEntity;
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

    public PlayerEntity findById(String id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    public PlayerEntity savePlayer(Player player) {
        return saveRustEntity(player);
    }

    public PlayerEntity savePlayer(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        return saveRustEntity(player);
    }

    private PlayerEntity saveRustEntity(Player player) {
        PlayerEntity byId = findById(player.getSteamID());

        if (byId != null && byId.getId() != null) {
            if (player.getUsername().equals(byId.getName())) {
                return byId;
            }
            byId.setName(byId.getName());
            repository.save(byId);
            return byId;
        }
        PlayerEntity entity = new PlayerEntity();
        entity.setId(player.getSteamID());
        entity.setName(player.getUsername());
        repository.saveAndFlush(entity);
        return entity;
    }

    public PlayerEntity findByDiscord(String discord) {
        return repository.findRustEntityByDiscord(discord);
    }

    public PlayerEntity addDiscordToEntity(String steamID, String discord) {
        PlayerEntity player = repository.findById(steamID).get();
        player.setDiscord(discord);
        return repository.saveAndFlush(player);
    }
}