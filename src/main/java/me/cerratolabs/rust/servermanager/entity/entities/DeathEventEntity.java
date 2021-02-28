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
    private PlayerEntity killer;

    @ManyToOne
    private PlayerEntity murdered;

    @Column
    private Long timestamp;

    @Column
    private DeathReason reason;

    @Column
    private String wipeVersion;

    @Column
    private Integer server;

}