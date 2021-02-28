package me.cerratolabs.rust.servermanager.rest.parsers;

import me.cerratolabs.rust.servermanager.entity.entities.ServerEntity;
import me.cerratolabs.rust.servermanager.rest.request.Server;

public class ServerParser {


    public static Server parseEntityToObjectWithoutPassword(ServerEntity entity) {
        Server server = new Server();
        server.setPassword("encripted");
        server.setAddress(entity.getAddress());
        server.setPort(entity.getPort());
        server.setStatus(entity.getStatus());
        server.setName(entity.getPublicName());
        server.setImage(entity.getImage());
        return server;
    }

    public static Server parseEntityToObject(ServerEntity entity) {
        Server server = new Server();
        server.setPassword(entity.getPassword());
        server.setAddress(entity.getAddress());
        server.setPort(entity.getPort());
        server.setStatus(entity.getStatus());
        server.setName(entity.getPublicName());
        server.setImage(entity.getImage());
        return server;
    }

    public static ServerEntity parseObjectToEntity(Server server) {
        ServerEntity entity = new ServerEntity();
        entity.setPassword(server.getPassword());
        entity.setAddress(server.getAddress());
        entity.setPort(server.getPort());
        entity.setStatus(server.getStatus());
        entity.setPublicName(server.getName());
        entity.setImage(server.getImage());
        return entity;
    }


}
