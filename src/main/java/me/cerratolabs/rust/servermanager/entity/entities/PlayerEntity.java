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
public class PlayerEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Long steamId;

    @Column
    private String name;

    @Column
    private String discord;

    @Column
    private Long creationDate;

    @Column
    private Long lastJoinDate;
}