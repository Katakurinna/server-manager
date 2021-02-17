package me.cerratolabs.rust.servermanager.entity.entities;

import lombok.Data;
import me.cerratolabs.rusrcon.entities.enums.DeathReason;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Table
@Entity
@Data
public class DeathEventEntity {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @ManyToOne
    private RustEntity killer;

    @ManyToOne
    private RustEntity murdered;

    @Column
    private Long timestamp;

    @Column
    private DeathReason reason;

    @Column
    private String wipeVersion;
}