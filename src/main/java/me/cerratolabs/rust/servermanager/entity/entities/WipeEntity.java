package me.cerratolabs.rust.servermanager.entity.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Table
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class WipeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private Long startDate;

    @Column
    private Long endDate;

    @Column
    private Long seed;

    @Column
    private Integer mapSize;

    @ManyToOne
    private ServerEntity server;
}