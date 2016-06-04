package com.github.apycazo.spring_rest_forge.sample.restRepository;

import com.github.apycazo.spring_rest_forge.sample.restRepository.components.RecordApplication;
import com.github.apycazo.spring_rest_forge.sample.restRepository.components.RecordModel;
import com.github.apycazo.spring_rest_forge.sample.restRepository.components.RecordRepository;
import com.github.apycazo.spring_rest_forge.sample.restRepository.components.RecordRestRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Starter for the repo tests
 * @author Andres Picazo
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { RecordApplication.class })
@WebAppConfiguration
@EnableAutoConfiguration
// Start server on a random port
@IntegrationTest({"server.port:0"})
// Enable the gateway
@TestPropertySource(properties = {
        "spring.data.rest.basePath=/test-api",
        "spring.jpa.show-sql:true"
})
public class SampleRestRepositoryTest
{

    @Autowired(required = false)
    private RecordRepository recordRepository;
    @Autowired(required = false)
    private RecordRestRepository recordRestRepository;

    @Test
    public void recordRepositoryExists ()
    {
        Assert.assertNotNull(RecordRepository.class.getSimpleName() + " not auto wired", recordRepository);
    }

    @Test
    public void recordRestRepositoryExists ()
    {
        Assert.assertNotNull(RecordRestRepository.class.getSimpleName() + " not wired", recordRestRepository);
    }

    @Test
    public void repositoryCrudOperations ()
    {
        RecordModel rm1 = new RecordModel();
        rm1.setName("model-1");
        rm1.setActive(true);

        RecordModel persisted;
        persisted = recordRepository.save(rm1);
        Assert.assertTrue("Id not correct", persisted.getId() > 0);
        Assert.assertTrue("TS value not set", persisted.getTs() > 0);

        RecordModel rm2 = new RecordModel();
        rm2.setName("model-2");
        rm2.setActive(true);

        persisted = recordRepository.save(rm2);
        Assert.assertTrue(persisted.getId() > 0);

        // Read one
        RecordModel fromPersistence = recordRepository.findOne(persisted.getId());
        Assert.assertNotNull("Fetched value is null", fromPersistence);
        Assert.assertEquals("Bad id on fetched record", persisted.getId(), fromPersistence.getId());

        Assert.assertEquals("Incorrect number of records on persistence", 2, recordRepository.count());

        recordRepository.deleteAll();
        Assert.assertEquals("Incorrect number of records on persistence", 0, recordRepository.count());

    }

}
