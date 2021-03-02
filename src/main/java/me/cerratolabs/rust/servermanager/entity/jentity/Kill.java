package me.cerratolabs.rust.servermanager.entity.jentity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.cerratolabs.rust.servermanager.entity.entities.PlayerEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Kill {

    private PlayerEntity killer;

    private PlayerEntity murdered;

    private Long date;

    private Long id;
}