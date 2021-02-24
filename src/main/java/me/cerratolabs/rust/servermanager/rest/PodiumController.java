package me.cerratolabs.rust.servermanager.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import me.cerratolabs.rust.servermanager.entity.services.DeathEventService;
import me.cerratolabs.rust.servermanager.entity.services.MessageEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1")
public class PodiumController {

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
    public String getPodium() {
        return mapper.writeValueAsString(deathEventService.getPodium());
    }

    @GetMapping("/chat")
    @SneakyThrows
    public String getChatFrom(@RequestParam String name) {
        return "Not implemented yet";//mapper.writeValueAsString(messageEntityService.getChat(name));
    }
}