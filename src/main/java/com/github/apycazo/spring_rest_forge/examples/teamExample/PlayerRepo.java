package com.github.apycazo.spring_rest_forge.examples.teamExample;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepo extends CrudRepository<Player, Long>
{
}
