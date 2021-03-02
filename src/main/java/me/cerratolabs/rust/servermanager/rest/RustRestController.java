package me.cerratolabs.rust.servermanager.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.cerratolabs.rust.servermanager.entity.jentity.Kill;
import me.cerratolabs.rust.servermanager.entity.jentity.PlayerStats;
import me.cerratolabs.rust.servermanager.entity.jentity.Podium;
import me.cerratolabs.rust.servermanager.entity.services.DeathEventService;
import me.cerratolabs.rust.servermanager.entity.services.MessageEntityService;
import me.cerratolabs.rust.servermanager.entity.services.PodiumService;
import me.cerratolabs.rust.servermanager.entity.services.ServerEntityService;
import me.cerratolabs.rust.servermanager.rest.exceptions.InvalidDataException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RustRestController {

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private DeathEventService deathEventService;

    @Autowired
    private PodiumService podiumService;

    @Autowired
    private MessageEntityService messageEntityService;

    @Autowired
    private ServerEntityService serverService;

    @GetMapping("/podium")
    public Podium getPodium(@RequestParam(name = "serverId", required = true) Integer serverId) {
        return podiumService.getPodium(serverService.findActiveServerByIdWithSecuredPassword(serverId));
    }

    @GetMapping("/stats")
    public PlayerStats getStatsAndSaveDiscordID(
        @RequestParam(name = "steamId", required = false) String steamId,
        @RequestParam(name = "discordId", required = false) String discordId,
        @RequestParam(name = "serverId", required = true) Integer serverId
    ) {
        if (Strings.isEmpty(steamId) && Strings.isEmpty(discordId)) {
            throw new InvalidDataException("Put steamid param or discordid param.");
        }
        if (Strings.isEmpty(steamId)) {
            return deathEventService.getPlayerStatsFromDiscordID(discordId, serverService.findActiveServerByIdWithSecuredPassword(serverId));
        }
        if (Strings.isEmpty(discordId)) {
            return deathEventService.getPlayerStatsFromSteamID(Long.parseLong(steamId), serverService.findActiveServerByIdWithSecuredPassword(serverId));
        }

        return deathEventService.getPlayerStatsAndSaveDiscordID(Long.parseLong(steamId), discordId, serverService.findActiveServerByIdWithSecuredPassword(serverId));

    }

    @GetMapping("/chat")
    public String getChatFrom(@RequestParam String name) {
        return "Not implemented yet";//messageEntityService.getChat(name);
    }

    @GetMapping("/kills")
    public List<Kill> getKillsFromServer(@RequestParam(name = "serverId", required = true) Integer serverId) {
        return deathEventService.getKillsFromServer(serverId);
    }
}