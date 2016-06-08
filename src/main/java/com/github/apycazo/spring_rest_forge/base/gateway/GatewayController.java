package com.github.apycazo.spring_rest_forge.base.gateway;

import com.github.apycazo.spring_rest_forge.base.etc.Constants;
import com.github.apycazo.spring_rest_forge.base.domains.Outcome;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Andres Picazo
 */
@Slf4j
@RestController
@CrossOrigin(origins = GatewayController.ORIGIN)
@RequestMapping(value = GatewayController.MAPPING)
@ConditionalOnProperty(prefix = Constants.PROPERTY_PREFIX, name = "gateway.enable")
public class GatewayController
{

    static final String MAPPING = "${" + Constants.PROPERTY_PREFIX + ".gateway.mapping:gateway}";
    static final String ORIGIN = "${" + Constants.PROPERTY_PREFIX + ".gateway.origin:*}";
    private final Map<String, GatewayService> serviceDirectory = new HashMap<>();

    //<editor-fold desc="JsonP controller advice">
    @ControllerAdvice
    protected static class JsonpAdvice extends AbstractJsonpResponseBodyAdvice
    {
        public JsonpAdvice()
        {
            super("callback");
        }
    }
    //</editor-fold>

    //<editor-fold desc="Exception handler">
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleExceptions(HttpServletRequest request, Exception e)
    {
        return Outcome
                .exception("Error on gateway path " + request.getRequestURL(), e)
                .asResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    //</editor-fold>

    //<editor-fold desc="Configure component">
    // Inject all gateway services found
    @Autowired(required = false)
    protected void configureServices(GatewayService[] services)
    {
        if (services != null) {
            serviceDirectory.clear();
            for (GatewayService service : services) {
                serviceDirectory.put(service.getMapping(), service);
                log.info("GatewayController mapped '{}' to '{}'", service.getMapping(), service.getClass().getName());
            }
        } else {
            log.warn("GatewayController '{}' implementations not found.", GatewayService.class.getName());
        }
    }
    //</editor-fold>

    //<editor-fold desc="List services">
    @RequestMapping(method = RequestMethod.GET)
    public Map<String, Map<String, String>> listServices(
            HttpServletRequest request)
            throws MalformedURLException
    {
        Map<String, Map<String, String>> response = new LinkedHashMap<>();

        String urlString = request.getRequestURL().toString();
        if (!urlString.endsWith("/")) urlString += "/";
        URL baseURL = new URL(urlString);

        serviceDirectory.keySet().stream().forEach(
                (e) -> {
                    Map<String, String> info = new LinkedHashMap<>();
                    try {
                        URL url = new URL(baseURL, e);
                        info.put("href", url.toString());
                    } catch (MalformedURLException ex) {
                        info.put("href", "");
                        info.put("ex", ex.getClass().getSimpleName());
                    } finally {
                        response.put(e, info);
                    }
                }
        );

        return response;
    }
    //</editor-fold>

    @RequestMapping(value = "{serviceName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> receive(
            HttpServletRequest request,
            @RequestBody(required = false) String body,
            @PathVariable String serviceName)
    {
        // Fetch service
        GatewayService service = serviceDirectory.get(serviceName);
        if (service == null) {
            String msg = "Service " + serviceName + " is not registered";
            return Outcome.failure(msg).asResponseEntity(HttpStatus.NOT_FOUND);
        }

        // Handle request
        try {
            String requestContent = Optional.ofNullable(body).orElse("");
            GatewayRequest gr = new GatewayRequest(request, requestContent);
            Object result = service.handleRequest(gr);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception processing service '{}", serviceName, e);
            return Outcome.exception(e).asResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
