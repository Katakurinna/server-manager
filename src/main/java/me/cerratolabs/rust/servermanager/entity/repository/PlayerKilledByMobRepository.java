package me.cerratolabs.rust.servermanager.entity.repository;

import me.cerratolabs.rust.servermanager.entity.entities.PlayerKilledByMob;
import me.cerratolabs.rust.servermanager.entity.entities.PlayerSeason;
import me.cerratolabs.rust.servermanager.entity.entities.ServerEntity;
import me.cerratolabs.rust.servermanager.entity.entities.WipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerKilledByMobRepository extends JpaRepository<PlayerKilledByMob, Integer> {

    Integer countAllByPlayerAndWipeAndServer(PlayerSeason player, WipeEntity wipe, ServerEntity server);

    Integer countAllByPlayerAndServer(PlayerSeason player, ServerEntity server);

}
