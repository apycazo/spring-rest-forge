package com.github.apycazo.spring_rest_forge.base.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.Advice;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Aspect to log anything for rest controllers inbound/outbound traffic.
 * @author Andres Picazo
 */
@Slf4j
@Aspect
@Component
class RestTrafficLoggerAspect
{

    @PostConstruct
    protected void init ()
    {
        log.info("Loading aspect '{}'", RestTrafficLoggerAspect.class.getSimpleName());
    }

    // This requires improvement before uploading

//    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
//    public void requestMapping() {}
//
//    @Before(value = "requestMapping() && args(body, httpServletRequest)")
//    public void logInbound (JoinPoint jp, String body, HttpServletRequest httpServletRequest)
//    {
//        log.info("Inbound request for '{}'", jp.getTarget().getClass().getSimpleName());
//    }

    @AfterReturning(
            pointcut="@annotation(org.springframework.web.bind.annotation.RequestMapping)",
            returning="returnValue")
    public void logOutbound (JoinPoint jp, Object returnValue)
    {
        returnValue = Optional.ofNullable(returnValue).orElse("<>");
        log.info("Outbound from '{}' :: '{}'", jp.getTarget().getClass().getSimpleName(), returnValue);
    }
}
