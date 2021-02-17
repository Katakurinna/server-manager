package me.cerratolabs.rust.servermanager.entity.repository;

import me.cerratolabs.rust.servermanager.entity.entities.DeathEventEntity;
import me.cerratolabs.rust.servermanager.entity.entities.RustEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DeathEventRepository extends JpaRepository<DeathEventEntity, String> {

    DeathEventEntity findAllByKiller(RustEntity killer);
    @Query("SELECT COUNT(murdered) FROM DeathEventEntity where murdered.id = :murdered")
    Integer countDeaths(@Param("murdered") String murdered);
}