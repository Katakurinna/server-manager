package me.cerratolabs.rust.servermanager.entity.entities;

import lombok.Data;
import me.cerratolabs.rustrcon.entities.enums.DeathReason;

import javax.persistence.*;

@Entity
@Table
@Data
public class PlayerKilledByMob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private PlayerSeason player;

    @Column
    private DeathReason reason;

    @ManyToOne
    private WipeEntity wipe;

    @ManyToOne
    private ServerEntity server;

    @Column
    private String mob;

}