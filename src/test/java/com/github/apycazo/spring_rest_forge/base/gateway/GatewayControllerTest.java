package com.github.apycazo.spring_rest_forge.base.gateway;

import com.github.apycazo.spring_rest_forge.base.gateway.components.GatewayApplication;
import com.github.apycazo.spring_rest_forge.base.gateway.components.GatewayTestService;
import com.jayway.restassured.RestAssured;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static com.jayway.restassured.RestAssured.given;

/**
  * @author Andres Picazo
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {GatewayApplication.class})
@WebAppConfiguration
@EnableAutoConfiguration
// Start server on a random port
@IntegrationTest({"server.port:0"})
// Enable the gateway
@TestPropertySource(properties = {"spring-rest-forge.gateway.enable:true"})
public class GatewayControllerTest
{
    @Autowired
    private Environment environment;

    @Value("${local.server.port}")
    private int port;

    @Value(GatewayController.MAPPING)
    private String gatewayMapping;

    @Autowired(required = false)
    private GatewayTestService gatewayTestService;

    @Autowired(required = false)
    private GatewayController gatewayController;

    @Before
    public void setupRestAssured ()
    {
        RestAssured.port = port;
    }

    @Test
    public void environmentMustHaveGatewayControllerEnabled ()
    {
        String propertyKey = "spring-rest-forge.gateway.enable";
        String expected = "true";
        Assert.assertEquals("Property not set: " + propertyKey, expected, environment.getProperty(propertyKey));
    }

    @Test
    public void gatewayControllerMustBePresent()
    {
        Assert.assertNotNull(GatewayController.class.getSimpleName() + " Not present", gatewayController);
    }

    @Test
    public void gatewayTestServiceMustBePresent()
    {
        Assert.assertNotNull(GatewayTestService.class.getSimpleName() + " Not present", gatewayTestService);
    }

    @Test
    public void gatewayMappingMustBePresent()
    {
        Assert.assertNotNull(gatewayMapping);
        Assert.assertNotEquals("", gatewayMapping);
    }

    @Test
    public void portMustBeSet()
    {
        Assert.assertTrue(port > 0);
    }

    @Test
    public void getRequestIsSuccessful ()
    {
        String url = "/" + gatewayMapping + "/" + GatewayTestService.mapping;

        given()
                .queryParam("param","value")
                .when()
                    .get(url)
                .then()
                    .statusCode(200);
    }

}