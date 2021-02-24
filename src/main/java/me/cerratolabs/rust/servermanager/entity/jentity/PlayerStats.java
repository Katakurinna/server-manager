package me.cerratolabs.rust.servermanager.entity.jentity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.cerratolabs.rust.servermanager.entity.entities.RustEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerStats {

    private RustEntity player;

    private String avatar;

    private Integer wipeKills;

    private Integer wipeDeaths;

    private Float wipeKDR;

    private Integer totalKills;

    private Integer totalDeaths;

    private Float totalKDR;

}