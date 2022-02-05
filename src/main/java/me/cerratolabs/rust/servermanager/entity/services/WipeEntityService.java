package me.cerratolabs.rust.servermanager.entity.services;

import me.cerratolabs.rust.servermanager.entity.entities.ServerEntity;
import me.cerratolabs.rust.servermanager.entity.entities.WipeEntity;
import me.cerratolabs.rust.servermanager.entity.jentity.Wipe;
import me.cerratolabs.rust.servermanager.entity.repository.WipeEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Service
public class WipeEntityService {

    @Autowired
    private WipeEntityRepository repository;

    @Autowired
    private EntityManager em;

    public void save(String name, Long startDate, Long endDate, Long seed, Integer mapSize, ServerEntity server) {
        WipeEntity entity = new WipeEntity();
        entity.setName(name);
        entity.setStartDate(startDate);
        entity.setEndDate(endDate);
        entity.setSeed(seed);
        entity.setMapSize(mapSize);
        entity.setServer(server);
        repository.save(entity);
    }

    public void save(Wipe wipe) {
        WipeEntity entity = new WipeEntity();
        entity.setName(wipe.getName());
        entity.setStartDate(wipe.getStartDate());
        entity.setEndDate(wipe.getEndDate());
        entity.setSeed(wipe.getSeed());
        entity.setMapSize(wipe.getMapSize());
        entity.setServer(wipe.getServer());
        repository.save(entity);
    }

    public WipeEntity save(WipeEntity wipeEntity) {
        return repository.save(wipeEntity);
    }

    public WipeEntity findWipeByServer(ServerEntity serverEntity) {
        return findWipeByServer(serverEntity.getId());
    }

    public WipeEntity findWipeByServer(Integer serverId) {
        Query query = em.createQuery("SELECT w FROM WipeEntity as w WHERE w.server=" + serverId + " ORDER BY w.startDate DESC");
        return (WipeEntity) query.setMaxResults(1).getSingleResult();
        //return repository.findFirstByServerOrderByStartDate(serverEntity);
    }


    public void remove(WipeEntity wipeEntity) {
        repository.delete(wipeEntity);
    }
}