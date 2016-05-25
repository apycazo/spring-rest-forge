package com.github.apycazo.spring_rest_forge.examples.teamExample;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@ToString
@Table(name = "teams")
public class Team
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Player> players = new HashSet<>();

}
