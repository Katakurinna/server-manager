package me.cerratolabs.rust.servermanager.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import me.cerratolabs.rust.servermanager.entity.jentity.PlayerStats;
import me.cerratolabs.rust.servermanager.entity.jentity.Podium;
import me.cerratolabs.rust.servermanager.entity.services.DeathEventService;
import me.cerratolabs.rust.servermanager.entity.services.MessageEntityService;
import me.cerratolabs.rust.servermanager.rest.exceptions.InvalidDataException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestController
public class RustRestController {

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private DeathEventService deathEventService;

    @Autowired
    private MessageEntityService messageEntityService;

    @GetMapping("/podium")
    public Podium getPodium() {
        return deathEventService.getPodium();
    }

    @GetMapping("/stats")
    public PlayerStats getStatsAndSaveDiscordID(
        @RequestParam(name = "steamId", required = false) String steamId,
        @RequestParam(name = "discordId", required = false) String discordId
    ) {
        if (Strings.isEmpty(steamId) && Strings.isEmpty(discordId)) {
            throw new InvalidDataException("Put steamid param or discordid param.");
        }
        if (Strings.isEmpty(steamId)) {
            return deathEventService.getPlayerStatsFromDiscordID(discordId);
        }
        if (Strings.isEmpty(discordId)) {
            return deathEventService.getPlayerStatsFromSteamID(steamId);
        }

        return deathEventService.getPlayerStatsAndSaveDiscordID(steamId, discordId);

    }

    @GetMapping("/chat")
    public String getChatFrom(@RequestParam String name) {
        return "Not implemented yet";//messageEntityService.getChat(name);
    }
}