package me.cerratolabs.rust.servermanager.entity.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Table
@Entity
public class PlayerSeason {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private PlayerEntity player;

    @Column
    private Long startDate;

    @Column
    private Long endDate;

}