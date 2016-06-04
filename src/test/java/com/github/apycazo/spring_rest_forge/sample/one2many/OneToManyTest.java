package com.github.apycazo.spring_rest_forge.sample.one2many;

import com.github.apycazo.spring_rest_forge.sample.one2many.components.OneToManyApplication;
import com.github.apycazo.spring_rest_forge.sample.restRepository.components.RecordApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author Andres Picazo
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {OneToManyApplication.class })
@WebAppConfiguration
@EnableAutoConfiguration
// Start server on a random port
@IntegrationTest({"server.port:0"})
// Enable the gateway
@TestPropertySource(properties = {
        "spring.jpa.show-sql:true"
})
public class OneToManyTest
{
    @Test
    public void empty ()
    {
        // just to avoid errors until all tests are in place
    }
}
