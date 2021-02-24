package me.cerratolabs.rust.servermanager.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import me.cerratolabs.rust.servermanager.entity.services.DeathEventService;
import me.cerratolabs.rust.servermanager.entity.services.MessageEntityService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.web.bind.annotation.RestController("/api/v1")
public class RustRestController {

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private DeathEventService deathEventService;

    @Autowired
    private MessageEntityService messageEntityService;

    @GetMapping("/podium")
    @SneakyThrows
    public String getPodium() {
        return mapper.writeValueAsString(deathEventService.getPodium());
    }

    @GetMapping("/stats")
    @SneakyThrows
    public String getStatsAndSaveDiscordID(@RequestParam(required = false) String steamid, @RequestParam(required = false) String discordid) {
        if(!Strings.isEmpty(steamid) && !Strings.isEmpty(steamid)) {
            return mapper.writeValueAsString(deathEventService.getPlayerStatsAndSaveDiscordID(steamid, discordid));
        }
        if(!Strings.isEmpty(steamid)) {
            return mapper.writeValueAsString(deathEventService.getPlayerStatsFromSteamID(steamid));
        }
        if(!Strings.isEmpty(discordid)) {
            return mapper.writeValueAsString(deathEventService.getPlayerStatsFromDiscordID(discordid));
        }
        return "Put steamID param or discordID param.";
    }

    @GetMapping("/chat")
    @SneakyThrows
    public String getChatFrom(@RequestParam String name) {
        return "Not implemented yet";//mapper.writeValueAsString(messageEntityService.getChat(name));
    }
}