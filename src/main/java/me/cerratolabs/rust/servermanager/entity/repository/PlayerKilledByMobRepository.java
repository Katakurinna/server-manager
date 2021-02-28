package me.cerratolabs.rust.servermanager.entity.repository;

import me.cerratolabs.rust.servermanager.entity.entities.PlayerKilledByMob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerKilledByMobRepository extends JpaRepository<PlayerKilledByMob, Integer> {
}
