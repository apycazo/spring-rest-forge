package com.github.apycazo.spring_rest_forge.base.gateway;

/**
 * @author Andres Picazo
 */
public interface GatewayService
{

    default String getMapping () {
        return this.getClass().getSimpleName().replaceAll(GatewayService.class.getSimpleName(), "");
    }

    Object handleRequest (GatewayRequest gr);
}