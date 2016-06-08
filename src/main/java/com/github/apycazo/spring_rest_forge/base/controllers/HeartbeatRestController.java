package com.github.apycazo.spring_rest_forge.base.controllers;

import com.github.apycazo.spring_rest_forge.base.domains.Outcome;
import com.github.apycazo.spring_rest_forge.base.etc.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @author Andres Picazo
 */
@Slf4j
@CrossOrigin
@RestController
@ConditionalOnProperty(prefix = Constants.PROPERTY_PREFIX, name = "heartbeat.enable")
public class HeartbeatRestController
{

    @RequestMapping(value = "/**/heartbeat")
    public Outcome heartbeat (@RequestBody(required = false) String body, HttpServletRequest request)
    {
        body = Optional.ofNullable(body).orElse("<>");
        log.info("Heartbeat '{}'", body);
        return new Outcome(true, "Heartbeat response", body);
    }
}
