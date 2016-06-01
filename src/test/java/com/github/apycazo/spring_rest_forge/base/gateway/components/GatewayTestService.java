package com.github.apycazo.spring_rest_forge.base.gateway.components;

import com.github.apycazo.spring_rest_forge.base.domains.Outcome;
import com.github.apycazo.spring_rest_forge.base.gateway.GatewayRequest;
import com.github.apycazo.spring_rest_forge.base.gateway.GatewayService;
import org.springframework.stereotype.Component;

/**
 * @author Andres Picazo
 */
@Component
public class GatewayTestService implements GatewayService
{

    public static String mapping = "gateway-test-service";

    @Override
    public String getMapping()
    {
        return mapping;
    }

    @Override
    public Object handleRequest(GatewayRequest gr)
    {
        return Outcome.success(mapping);
    }
}
