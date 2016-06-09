package com.github.apycazo.spring_rest_forge.base.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * Aspect to log anything for rest controllers outbound traffic.
 * @author Andres Picazo
 */
@Slf4j
@Aspect
@Component
class RestTrafficLoggerAspect
{
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @PostConstruct
    protected void init ()
    {
        log.info("Loading aspect '{}'", RestTrafficLoggerAspect.class.getSimpleName());
    }

    // This requires improvement before uploading

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void requestMappingPointcut() {}

    @AfterReturning(
            pointcut="@annotation(org.springframework.web.bind.annotation.RequestMapping)",
            returning="returnValue")
    public void logOutbound (JoinPoint jp, Object returnValue)
    {
        log.debug("Status {} on '{}?{}' ({}) returned '{}'",
                response.getStatus(),
                request.getRequestURI(),
                request.getQueryString(),
                jp.getTarget().getClass().getSimpleName(),
                Optional.ofNullable(returnValue).orElse("<>"));
    }
}
