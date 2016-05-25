package com.github.apycazo.spring_rest_forge.examples.teamExample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.swing.text.html.HTMLDocument;
import java.util.HashSet;
import java.util.Iterator;

@Slf4j
@Component
public class Showcase
{
    @Autowired
    private PlayerRepo playerRepo;

    @Autowired
    private TeamRepo teamRepo;

    @PostConstruct
    @Transactional
    protected void __init__ ()
    {
        Team team = new Team();
        team.setName("warriors");
        team.setPlayers(new HashSet<>());
        team = teamRepo.save(team);

        Player player1 = new Player();
        player1.setName("one");
        player1.setTeamId(team.getId());
        playerRepo.save(player1);

        team.getPlayers().add(player1);

        Team persistedTeam = teamRepo.save(team);
        log.info("Team: '{}'", persistedTeam.toString());

        Iterator<Team> it = teamRepo.findAll().iterator();
        while (it.hasNext()) {
            log.info("Persisted team: '{}'", it.next().toString());
        }

    }
}
