package me.cerratolabs.rust.servermanager.rest.services;

import me.cerratolabs.rust.servermanager.entity.entities.DeathEventEntity;
import me.cerratolabs.rust.servermanager.entity.entities.ServerEntity;
import me.cerratolabs.rust.servermanager.entity.jentity.Kill;
import me.cerratolabs.rust.servermanager.entity.services.DeathEventService;
import me.cerratolabs.rust.servermanager.entity.services.ServerEntityService;
import me.cerratolabs.rust.servermanager.entity.services.WipeEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerKillService {

    @Autowired
    private ServerEntityService serverService;

    @Autowired
    private DeathEventService deathService;

    @Autowired
    private WipeEntityService wipeService;

    public List<Kill> getKillsFromServer(ServerEntity server) {
        List<DeathEventEntity> deathEventEntityList = deathService.findAllByServerAndWipeOrderByTimestampDesc(server, wipeService.findWipeByServer(server));
        return deathEventEntityList.stream().map(this::parseDeathEventToKill).collect(Collectors.toList());
    }

    public List<Kill> getKillsFromServer(Integer serverId) {
        return getKillsFromServer(serverService.findActiveServerByIdWithSecuredPassword(serverId));
    }

    private Kill parseDeathEventToKill(DeathEventEntity event) {
        return new Kill(event.getKiller().getPlayer(), event.getMurdered().getPlayer(), event.getTimestamp(), event.getId());
    }

}