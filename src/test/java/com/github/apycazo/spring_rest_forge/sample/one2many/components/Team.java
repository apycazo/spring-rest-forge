package com.github.apycazo.spring_rest_forge.sample.one2many.components;

import lombok.Data;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * @author Andres Picazo
 */
@Entity
@Data
public class Team
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;

    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Player> players;

    public static Team instance (String name)
    {
        Team team = new Team();
        team.name = name;
        team.players = new LinkedList<>();
        return team;
    }

    @Override
    public String toString ()
    {
        return String.format("%d:%s (%d)", id, name, Optional.ofNullable(players.size()).orElse(0));
    }
}
