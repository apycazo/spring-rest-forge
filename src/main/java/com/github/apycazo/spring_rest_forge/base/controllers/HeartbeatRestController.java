package com.github.apycazo.spring_rest_forge.base.controllers;

import com.github.apycazo.spring_rest_forge.base.domains.Outcome;
import com.github.apycazo.spring_rest_forge.base.etc.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @author Andres Picazo
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/**/heartbeat")
@ConditionalOnProperty(prefix = Constants.PROPERTY_PREFIX, name = "heartbeat.enable")
public class HeartbeatRestController
{

    @RequestMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Outcome heartbeat (@RequestBody(required = false) String body, HttpServletRequest request)
    {
        body = Optional.ofNullable(body).orElse("<>");
        log.info("Heartbeat '{}'", body);
        return new Outcome(true, "Heartbeat response", body);
    }

    /**
     * You ok? service query, just returns a 200 OK
     */
    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "u-ok")
    public void uok ()
    {
        // does nothing, just oks
        log.info("Service OK");
    }

    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "u-nok")
    public void unok () throws Exception
    {
        throw new Exception("Not OK");
    }
}
