package me.cerratolabs.rust.servermanager.entity.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Entity
@Data
public class RustEntity {

    @Id
    private String id;

    @Column
    private String name;
}