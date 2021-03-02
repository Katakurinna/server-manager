package me.cerratolabs.rust.servermanager.entity.entities;

import lombok.Data;
import me.cerratolabs.rust.servermanager.entity.jentity.ServerStatus;

import javax.persistence.*;

@Entity
@Table
@Data
public class ServerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String address;

    @Column
    private Integer port;

    @Column
    private String password;

    @Enumerated
    @Column
    private ServerStatus status;

    @Column
    private String publicName;

    @Column
    private String image;

    @Column
    private String passwordSalt;

}