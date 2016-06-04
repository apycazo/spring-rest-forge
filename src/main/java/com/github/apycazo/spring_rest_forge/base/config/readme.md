# Original sources for custom h2

Until some tests are written, note that the custom h2 configuration previously consisted on three files:

## H2Properties.java

```java
@Data
@ConfigurationProperties(prefix = "h2")
public class H2Properties {

    public boolean embedded = true;
    public boolean generateDdl = true;
    public String url = "jdbc:h2:file:./h2db";
    public String scan = H2Properties.class.getPackage().getName();
}
```

## H2Config.java

```java
@Slf4j
@Configuration(value = "h2-config")
@EnableConfigurationProperties(H2Properties.class)
public class H2Config {

    @Autowired
    protected H2Properties h2Properties;

    public H2Config() {
        log.info("Setting up custom H2 db");
    }

    @PostConstruct
    protected void report ()
    {
        log.info("H2 embedded mode  : {}", h2Properties.isEmbedded());
        log.info("H2 generating DDL : {}", h2Properties.isGenerateDdl());
        log.info("H2 scan path      : {}", h2Properties.getScan());
        if (!h2Properties.isEmbedded()) {
            log.info("H2 url            : {}", h2Properties.getScan());
        }
    }

    @Bean
    ServletRegistrationBean h2servletRegistration(){
        // Enable console
        ServletRegistrationBean registrationBean = new ServletRegistrationBean( new WebServlet());
        registrationBean.addUrlMappings("/console/*");
        return registrationBean;
    }

    // The following is to allow this repository to coexist with another database.

    @Bean
    PlatformTransactionManager h2dbTransactionManager() {
        return new JpaTransactionManager(h2dbEntityManagerFactory().getObject());
    }

    @Bean
    LocalContainerEntityManagerFactoryBean h2dbEntityManagerFactory()
    {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setGenerateDdl(h2Properties.isGenerateDdl());

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();

        factoryBean.setDataSource(h2dbDataSource());
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        factoryBean.setPackagesToScan(h2Properties.getScan());

        return factoryBean;
    }

    @Bean
    DataSource h2dbDataSource()
    {
        if (h2Properties.isEmbedded()) {
            return new EmbeddedDatabaseBuilder()
                    .setType(EmbeddedDatabaseType.H2)
                    .build();
        }
        else {
            JdbcDataSource ds = new JdbcDataSource();
            ds.setUrl(h2Properties.getUrl());

            return ds;
        }
    }
}
```

## H2Enable.java

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import(H2Config.class)
@Configuration(value = "h2-enable-config")
@EnableJpaRepositories(
        entityManagerFactoryRef = "h2dbEntityManagerFactory",
        transactionManagerRef = "h2dbTransactionManager"
)
public @interface H2Enable {
    // This is just to start H2 without explicitly writing the @EnableJpaRepositories
}
```