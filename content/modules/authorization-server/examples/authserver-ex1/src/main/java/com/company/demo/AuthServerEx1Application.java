package com.company.demo;

import com.company.demo.entity.User;
import com.google.common.base.Strings;
import io.jmix.authserver.service.mapper.DefaultOAuth2TokenUserMixin;
import io.jmix.authserver.service.mapper.JdbcOAuth2AuthorizationServiceObjectMapperCustomizer;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@SpringBootApplication
public class AuthServerEx1Application {

    @Autowired
    private Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(AuthServerEx1Application.class, args);
    }

    @Bean
    @Primary
    @ConfigurationProperties("main.datasource")
    DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("main.datasource.hikari")
    DataSource dataSource(final DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    // tag::tokenObjectMapperCustomizer[]
    @Bean
    JdbcOAuth2AuthorizationServiceObjectMapperCustomizer tokenObjectMapperCustomizer() {
        return objectMapper ->
                objectMapper.addMixIn(User.class, DefaultOAuth2TokenUserMixin.class);
    }
    // end::tokenObjectMapperCustomizer[]

    @EventListener
    public void printApplicationUrl(final ApplicationStartedEvent event) {
        LoggerFactory.getLogger(AuthServerEx1Application.class).info("Application started at "
                + "http://localhost:"
                + environment.getProperty("local.server.port")
                + Strings.nullToEmpty(environment.getProperty("server.servlet.context-path")));
    }
}
