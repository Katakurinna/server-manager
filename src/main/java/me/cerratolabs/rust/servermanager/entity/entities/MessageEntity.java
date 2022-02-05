package me.cerratolabs.rust.servermanager.entity.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String message;

    @Column
    private Integer identifier;

    @Column
    private String stacktrace;

    @Column
    private String type;

    @ManyToOne
    private WipeEntity wipe;

    @ManyToOne
    private ServerEntity server;

}