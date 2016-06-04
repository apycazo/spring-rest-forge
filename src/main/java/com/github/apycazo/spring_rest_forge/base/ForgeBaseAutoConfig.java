package com.github.apycazo.spring_rest_forge.base;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * When used this project is used as a dependency, makes sure the components are loaded
 * @author Andres Picazo
 */
@Configuration(value = "spring-rest-forge:auto-config")
@ComponentScan(basePackageClasses = {ForgeBaseAutoConfig.class})
public class ForgeBaseAutoConfig
{
    // EMPTY
}
