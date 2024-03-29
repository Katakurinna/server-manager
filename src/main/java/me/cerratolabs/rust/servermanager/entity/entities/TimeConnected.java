package me.cerratolabs.rust.servermanager.entity.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeConnected {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private PlayerEntity player;

    @Column
    private Long join;

    @Column
    private Long left;

    @Column
    private Long connectedMillis;

    @ManyToOne
    private WipeEntity wipe;

    @ManyToOne
    private ServerEntity server;

}