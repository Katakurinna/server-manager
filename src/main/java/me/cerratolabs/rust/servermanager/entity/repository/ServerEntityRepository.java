package me.cerratolabs.rust.servermanager.entity.repository;

import me.cerratolabs.rust.servermanager.entity.entities.ServerEntity;
import me.cerratolabs.rust.servermanager.entity.jentity.ServerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServerEntityRepository extends JpaRepository<ServerEntity, Integer> {

    List<ServerEntity> findAllByStatus(ServerStatus status);

    Boolean existsServerEntityByAddressAndPort(String address, Integer port);

}
