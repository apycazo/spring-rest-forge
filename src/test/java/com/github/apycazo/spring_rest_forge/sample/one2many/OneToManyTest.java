package com.github.apycazo.spring_rest_forge.sample.one2many;

import com.github.apycazo.spring_rest_forge.sample.one2many.components.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Andres Picazo
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {OneToManyApplication.class })
//@WebAppConfiguration
@EnableAutoConfiguration
// Start server on a random port
@IntegrationTest({"server.port:0"})
// Enable the gateway
@TestPropertySource(properties = {
        "spring.jpa.show-sql:true"
})
public class OneToManyTest
{

    @Autowired
    private TeamRepository teamRepository;

    @Test
    public void teamRepositoryExists ()
    {
        Assert.assertNotNull(TeamRepository.class.getSimpleName() + " not auto wired", teamRepository);
    }

    @Test
    public void teamPersistenceChecks ()
    {
        Team team = Team.instance("City Team");

        Player p1 = Player.instance("player one", team);
        Player p2 = Player.instance("player two", team);

        team.getPlayers().add(p1);
        team.getPlayers().add(p2);

        Integer id = teamRepository.save(team).getId();
        // Fetch by id
        Team persisted = teamRepository.findOne(id);

        Assert.assertNotNull("Persisted value is null", persisted);
        Assert.assertNotNull("Persisted list is null", persisted.getPlayers());
        Assert.assertFalse("List has no elements", persisted.getPlayers().isEmpty());
        Assert.assertEquals("List does not contain expected elements", 2, persisted.getPlayers().size());

        log.info("[{}] '{}' ('{}' players) ", persisted.getId(), persisted.getName(), persisted.getPlayers().size());
        persisted.getPlayers().forEach(player -> log.info("Player ({}) {}", player.getId(), player.getName()));
    }
}
