package com.github.apycazo.spring_rest_forge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author Andres Picazo
 */
@SpringBootApplication
@EnableAspectJAutoProxy
public class Application
{

    public static void main (String [] args)
    {
        SpringApplication.run(Application.class, args);
    }
}
