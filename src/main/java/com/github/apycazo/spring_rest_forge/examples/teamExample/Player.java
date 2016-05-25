package com.github.apycazo.spring_rest_forge.examples.teamExample;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@ToString
@Table(name = "players")
public class Player
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;


    private Long teamId;

//    @ManyToOne
//    @JoinColumn(name = "team_id")
//    private Team team;

}
