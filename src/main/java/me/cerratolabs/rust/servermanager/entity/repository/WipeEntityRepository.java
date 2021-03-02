package me.cerratolabs.rust.servermanager.entity.repository;

import me.cerratolabs.rust.servermanager.entity.entities.ServerEntity;
import me.cerratolabs.rust.servermanager.entity.entities.WipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WipeEntityRepository extends JpaRepository<WipeEntity, Integer> {

    WipeEntity findFirstByServerOrderByStartDate(ServerEntity server);
}