package com.github.apycazo.spring_rest_forge.base.config;

import com.github.apycazo.spring_rest_forge.base.Constants;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.server.web.WebServlet;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @author Andres Picazo
 */
@Data
@Slf4j
@Configuration(value = "spring-rest-forge:custom-h2:config")
@ConfigurationProperties(prefix = Constants.PROPERTY_PREFIX + ".customH2")
@ConditionalOnProperty(prefix = Constants.PROPERTY_PREFIX, name = "customH2.enable")
@EnableJpaRepositories(
        entityManagerFactoryRef = "h2dbEntityManagerFactory",
        transactionManagerRef = "h2dbTransactionManager"
)
public class H2CustomConfig
{
    public boolean embedded = true;
    public boolean generateDdl = true;
    public String url = "jdbc:h2:file:./h2db";
    public String scan = H2CustomConfig.class.getPackage().getName();

    @Bean
    ServletRegistrationBean h2servletRegistration() {
        // Enable console
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
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
        jpaVendorAdapter.setGenerateDdl(generateDdl);

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();

        factoryBean.setDataSource(h2dbDataSource());
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        factoryBean.setPackagesToScan(scan);

        return factoryBean;
    }

    @Bean
    DataSource h2dbDataSource()
    {
        if (embedded) {
            return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
        }
        else {
            JdbcDataSource ds = new JdbcDataSource();
            ds.setUrl(url);
            return ds;
        }
    }
}
