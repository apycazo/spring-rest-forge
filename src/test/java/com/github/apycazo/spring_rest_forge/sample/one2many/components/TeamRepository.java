package com.github.apycazo.spring_rest_forge.sample.one2many.components;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Andres Picazo
 */
@Repository
public interface TeamRepository extends JpaRepository<Team, Integer>
{
}
