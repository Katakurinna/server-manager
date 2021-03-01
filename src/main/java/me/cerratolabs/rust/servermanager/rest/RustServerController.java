package me.cerratolabs.rust.servermanager.rest;

import com.sun.istack.NotNull;
import lombok.SneakyThrows;
import me.cerratolabs.rust.servermanager.entity.services.ServerEntityService;
import me.cerratolabs.rust.servermanager.rest.exceptions.InvalidDataException;
import me.cerratolabs.rust.servermanager.rest.exceptions.ServerAlreadyExistException;
import me.cerratolabs.rust.servermanager.rest.parsers.ServerParser;
import me.cerratolabs.rust.servermanager.rest.request.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/servers")
public class RustServerController {

    @Autowired
    private ServerEntityService serverEntityService;

    @SneakyThrows
    @PostMapping("/server/new")
    public void newServer(@NotNull @RequestBody Server server) {
        if (server == null) throw new InvalidDataException("You need a json body to be able to work");
        if (server.containsNulls())
            throw new InvalidDataException("I need all the parameters to be able to save the server.");
        if (serverEntityService.existServer(server.getAddress(), server.getPort()))
            throw new ServerAlreadyExistException("This server is currently on database");
        serverEntityService.save(ServerParser.parseObjectToEntity(server));
    }

    @GetMapping("server/list")
    public List<Server> getActiveServers() {
        return serverEntityService.findActiveServer().stream().map(ServerParser::parseEntityToObjectWithoutPassword).collect(Collectors.toList());
    }

}