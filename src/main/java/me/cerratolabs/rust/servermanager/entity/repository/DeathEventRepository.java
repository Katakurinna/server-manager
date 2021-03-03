package me.cerratolabs.rust.servermanager.entity.repository;

import me.cerratolabs.rust.servermanager.entity.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeathEventRepository extends JpaRepository<DeathEventEntity, String> {

    DeathEventEntity findAllByKiller(PlayerEntity killer);

    @Query("SELECT COUNT(murdered) FROM DeathEventEntity where murdered.id = :murdered")
    Integer countDeaths(@Param("murdered") Integer murdered);

    List<DeathEventEntity> findAllByServerAndWipeOrderByTimestampDesc(ServerEntity server, WipeEntity wipe);

    Integer countAllByKillerAndWipeAndServer(PlayerSeason killer, WipeEntity wipe, ServerEntity server);

    Integer countAllByKillerAndServer(PlayerSeason killer, ServerEntity server);

    Integer countAllByMurderedAndWipeAndServer(PlayerSeason killer, WipeEntity wipe, ServerEntity server);

    Integer countAllByMurderedAndServer(PlayerSeason murdered, ServerEntity server);

}