package me.cerratolabs.rust.servermanager.entity.jentity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.cerratolabs.rust.servermanager.entity.entities.ServerEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wipe {

    private String name;

    private Long startDate;

    private Long endDate;

    private Long seed;

    private Integer mapSize;

    private ServerEntity server;
}