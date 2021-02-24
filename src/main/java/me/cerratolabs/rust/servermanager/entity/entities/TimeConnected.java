package me.cerratolabs.rust.servermanager.entity.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Table
@Entity
@Data
public class TimeConnected {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @ManyToOne
    private RustEntity player;

    @Column
    private Long join;

    @Column
    private Long left;

    @Column
    private Long connectedMillis;

    @Column
    private String wipeVersion;

}