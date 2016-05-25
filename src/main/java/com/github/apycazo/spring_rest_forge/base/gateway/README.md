## Gateway usage and configuration

# Introduction
The 'Gateway' component is meant to provide a quick way to implement simple rest services.

# Creating services

To create a service to be used by the gateway, simply create a @Component implementing the 'GatewayService' interface.

An example:

```java
@Slf4j
@Component
public class GatewayDemoService implements GatewayService {

    @Override
    public Object handleRequest(GatewayRequest gr)
    {
        log.info("Received request on {}", GatewayDemoService.class.getName());
        return Outcome.success();
    }
}
```

# Mapping

By default, the class name will be used to map the service, unless the method 'getMapping' os overriden.

An example:

```java
@Slf4j
@Component
public class GatewayDemoService implements GatewayService {

    @Override
    public Object handleRequest(GatewayRequest gr)
    {
        log.info("Received request on {}", GatewayDemoService.class.getName());
        return Outcome.success();
    }

    @Override
    String getMapping () {
        return "alternateMapping";
    }
}
```

# Properties

Property root is taken from 'Constants', which defaults to 'spring-rest-forge'. Properties are:

| Property                 | Default value | Description                                          |
|--------------------------|---------------|------------------------------------------------------|
| <root>.gateway.mapping   | gateway       | Where to map the requests for the gateway controller |
| <root>.gateway.origin    | *             | Used for the cross-domain origins                    |
| <root>.gateway.enable    | true          | Whenever this service should be loaded or not        |