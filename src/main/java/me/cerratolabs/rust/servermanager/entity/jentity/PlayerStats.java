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

    private String img_url;

    private Integer kills;

    private Integer deaths;

    private Float KDR;

}