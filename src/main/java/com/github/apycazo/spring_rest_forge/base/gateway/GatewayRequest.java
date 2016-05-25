package com.github.apycazo.spring_rest_forge.base.gateway;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * A class to wrap all elements of a gateway service request
 * @author Andres Picazo
 */
@Data
public class GatewayRequest {

    private HttpServletRequest httpRequest;
    private Optional<String> httpBody;
    private RequestMethod httpMethod;

    public GatewayRequest () {}

    public GatewayRequest (HttpServletRequest httpRequest, Optional<String> httpBody)
    {
        this.httpRequest = httpRequest;
        this.httpBody = httpBody != null ? httpBody : Optional.empty();
        httpMethod = RequestMethod.valueOf(httpRequest.getMethod());
    }

}
