package me.cerratolabs.rust.servermanager.entity.entities;

import lombok.Data;

import javax.persistence.*;

@Table
@Entity
@Data
public class PlayerAddressesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private PlayerEntity player;

    @Column
    private String address;

    @Column
    private Long lastTimeUsed;

}
