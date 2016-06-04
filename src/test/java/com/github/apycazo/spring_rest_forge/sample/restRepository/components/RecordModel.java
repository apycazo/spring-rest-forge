package com.github.apycazo.spring_rest_forge.sample.restRepository.components;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

/**
 * A simple data model to record on persistence.
 * @author Andres Picazo
 */
@Data
@ToString
@Entity
@Table(name = "records")
public class RecordModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "name", length = 64, nullable = false)
    private String name;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "ts", nullable = false)
    private long ts;

    @PrePersist
    @PreUpdate
    public void beforePersisting ()
    {
        ts = ts <= 0 ? System.currentTimeMillis() : ts;
    }
}
