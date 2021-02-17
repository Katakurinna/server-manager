package me.cerratolabs.rust.servermanager.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import me.cerratolabs.rust.servermanager.entity.services.DeathEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1")
public class PodiumController {

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private DeathEventService deathEventService;

    @GetMapping("/podium")
    @SneakyThrows
    public String getPodium() {
        return mapper.writeValueAsString(deathEventService.getPodium());
    }
}