package me.cerratolabs.rust.servermanager.entity.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class ServerAdministrators {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private ServerEntity server;

    @ManyToOne
    private PlayerEntity player;

}