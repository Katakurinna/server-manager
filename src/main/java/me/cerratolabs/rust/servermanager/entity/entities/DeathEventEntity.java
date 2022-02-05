package me.cerratolabs.rust.servermanager.entity.entities;

import lombok.Data;
import me.cerratolabs.rustrcon.entities.enums.DeathReason;

import javax.persistence.*;

@Table
@Entity
@Data
public class DeathEventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private PlayerSeason killer;

    @ManyToOne
    private PlayerSeason murdered;

    @Column
    private boolean headshot;

    @Column
    private float distance;

    @Column
    private String weapon;

    @Column
    private Long timestamp;

    @Column
    private DeathReason reason;


    @ManyToOne
    private WipeEntity wipe;

    @ManyToOne
    private ServerEntity server;

}