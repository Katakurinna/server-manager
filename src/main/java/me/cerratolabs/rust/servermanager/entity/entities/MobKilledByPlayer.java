package me.cerratolabs.rust.servermanager.entity.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class MobKilledByPlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private PlayerSeason player;

    @Column
    private String mob;

    @Column
    private Long timestamp;

    @ManyToOne
    private WipeEntity wipe;

    @ManyToOne
    private ServerEntity server;
}