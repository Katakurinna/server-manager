package me.cerratolabs.rust.servermanager.entity.repository;

import me.cerratolabs.rust.servermanager.entity.entities.MobKilledByPlayer;
import me.cerratolabs.rust.servermanager.entity.entities.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MobKilledByPlayerRepository extends JpaRepository<MobKilledByPlayer, Integer> {
}
