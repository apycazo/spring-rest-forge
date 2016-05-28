package com.github.apycazo.spring_rest_forge.base.config;

import com.github.apycazo.spring_rest_forge.base.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Captures and registers Interceptor Handlers.
 * @author Andres Picazo
 */
@Slf4j
@Configuration(value = "spring-rest-forge:interceptor:config")
@ConditionalOnProperty(prefix = Constants.PROPERTY_PREFIX, name = "registerInterceptors.enable")
public class InterceptorConfig extends WebMvcConfigurerAdapter
{

    @Autowired(required = false)
    private HandlerInterceptor[] interceptors;

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        if (interceptors == null || interceptors.length == 0) {
            log.info("No interceptors to register");
        }
        else {
            for (HandlerInterceptor hi : interceptors) {
                registry.addInterceptor(hi);
                log.info("Registered '{}'", hi.getClass().getName());
            }
        }
    }
}
