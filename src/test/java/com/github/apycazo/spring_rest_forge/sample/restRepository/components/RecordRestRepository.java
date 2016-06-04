package com.github.apycazo.spring_rest_forge.sample.restRepository.components;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Similar to 'RecordRepository', but with a rest api.
 * @author Andres Picazo
 */
@RepositoryRestResource
public interface RecordRestRepository extends PagingAndSortingRepository<RecordModel, Long>
{
}
