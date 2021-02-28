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
    private PlayerEntity player;

    @Column
    private DeathReason reason;
}