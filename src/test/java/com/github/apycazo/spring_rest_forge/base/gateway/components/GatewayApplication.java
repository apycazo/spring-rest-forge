package com.github.apycazo.spring_rest_forge.base.gateway.components;

import com.github.apycazo.spring_rest_forge.base.ForgeBaseAutoConfig;
import com.github.apycazo.spring_rest_forge.base.gateway.GatewayController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Andres Picazo
 */
@SpringBootApplication(
        scanBasePackageClasses = {GatewayApplication.class, GatewayController.class},
        exclude = ForgeBaseAutoConfig.class
)
public class GatewayApplication
{
    public static void main (String [] args)
    {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
