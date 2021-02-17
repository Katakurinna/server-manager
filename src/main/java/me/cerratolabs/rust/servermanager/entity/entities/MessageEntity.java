package me.cerratolabs.rust.servermanager.entity.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table
@Data
public class MessageEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column
    private String message;

    @Column
    private Integer identifier;

    @Column
    private String stacktrace;

    @Column
    private String type;

}