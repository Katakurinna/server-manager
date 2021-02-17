package me.cerratolabs.rust.servermanager.entity.jentity.podium;

import lombok.Data;

@Data
public class PodiumPlayer {

    private String name;
    private String steamID;
    private Integer podiumPosition;
    private Integer kills;
    private Integer deaths;

}