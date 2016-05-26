package com.github.apycazo.spring_rest_forge.base;

import com.github.apycazo.spring_rest_forge.base.domains.Outcome;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Andres Picazo
 */
@Slf4j
@RestController
@CrossOrigin
public class HeartbeatRestController
{

    @RequestMapping(value = "/**/heartbeat")
    public Outcome heartbeat (@RequestBody(required = false) String body)
    {
        log.info("Heartbeat '{}'", StringUtils.isEmpty(body) ? "<empty>" : body);
        return Outcome.success();
    }
}
