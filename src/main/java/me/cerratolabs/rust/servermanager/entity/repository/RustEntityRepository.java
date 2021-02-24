package me.cerratolabs.rust.servermanager.entity.repository;

import me.cerratolabs.rust.servermanager.entity.entities.RustEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RustEntityRepository extends JpaRepository<RustEntity, String> {

    RustEntity findFirstByName(String name);

    List<RustEntity> findAllByName(String name);

    RustEntity findRustEntityByDiscord(String discord);

}