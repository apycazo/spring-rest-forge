# spring-rest-forge
Base project for a spring rest service. Some utilities are inside the package 'base', and examples on how to code some typical services can be found under the 'test' package.

A spring.factories is included in case this project is used as a dependency, instead of a template.

A sample properties file is as follows:

```yaml
server:
  port: 8080
  contextPath: /

logging:
  # Uncomment file to enable file logging
  # file: app.log
  level:
    com.github.apycazo: DEBUG

spring-rest-forge:
  customH2:
    enable: false
    embedded: true
    generateDdl: true
    url: jdbc:h2:file:./h2db
    scan: com.github.apycazo
  gateway:
    enable: false
    mapping: gateway
    origin: '*'
  heartbeat.enable: false
  registerInterceptors.enable: false
```

This project has the following dependencies:

1. spring-boot-starter-web
2. spring-boot-starter-data-jpa
3. spring-boot-starter-data-rest
4. lombok
5. h2 [optional]