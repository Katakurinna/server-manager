package me.cerratolabs.rust.servermanager.entity.jentity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.cerratolabs.rust.servermanager.entity.jentity.podium.PodiumPlayer;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Podium {

    private List<PodiumPlayer> podium;

}