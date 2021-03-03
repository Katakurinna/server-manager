package me.cerratolabs.rust.servermanager.entity.services;

import me.cerratolabs.rust.servermanager.entity.entities.PlayerEntity;
import me.cerratolabs.rust.servermanager.entity.entities.PlayerSeason;
import me.cerratolabs.rust.servermanager.entity.entities.WipeEntity;
import me.cerratolabs.rust.servermanager.entity.repository.PlayerSeasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

@Service
public class PlayerSeasonService {

    @Autowired
    private PlayerSeasonRepository repository;

    @Autowired
    private EntityManager em;

    public PlayerSeason save(PlayerEntity entity) {
        PlayerSeason playerSeason = new PlayerSeason();
        playerSeason.setPlayer(entity);
        playerSeason.setStartDate(System.currentTimeMillis());
        return repository.save(playerSeason);
    }

    public PlayerSeason tfindMostRecentlyPlayerSeason(PlayerEntity entity) {
        PlayerSeason season = null;
        try {
            Query query = em.createQuery("SELECT ps FROM PlayerSeason as ps WHERE ps.player=" + entity.getId() + " ORDER BY ps.startDate DESC");
            season = (PlayerSeason) query.setMaxResults(1).getSingleResult();
            //PlayerSeason season = repository.findFirstByPlayerOrderByStartDateDesc(entity);

        } catch (NoResultException ignored) {
        }

        if (season == null) return save(entity);
        return season;
    }

    public PlayerSeason findById(Integer id) {
        return repository.findById(id).orElseThrow();
    }
}