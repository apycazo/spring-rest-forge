package com.github.apycazo.spring_rest_forge.sample.restRepository.components;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @autor Andres Picazo
 */
@Repository
public interface RecordRepository extends CrudRepository<RecordModel, Long>
{
}
