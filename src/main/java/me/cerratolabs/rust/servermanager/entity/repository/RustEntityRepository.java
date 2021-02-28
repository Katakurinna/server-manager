package me.cerratolabs.rust.servermanager.entity.repository;

import me.cerratolabs.rust.servermanager.entity.entities.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RustEntityRepository extends JpaRepository<PlayerEntity, String> {

    PlayerEntity findBySteamId(Long steamId);

    PlayerEntity findFirstByName(String name);

    List<PlayerEntity> findAllByName(String name);

    PlayerEntity findRustEntityByDiscord(String discord);

}