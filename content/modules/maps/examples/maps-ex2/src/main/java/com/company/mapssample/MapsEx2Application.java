package com.company.mapssample;

import com.google.common.base.Strings;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import io.jmix.mapsflowui.component.projection.GeoMapProjectionRegistration;
import io.jmix.mapsflowui.component.projection.ProjectionRegistration;
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

@Push
@Theme(value = "maps-ex2")
@PWA(name = "Maps Ex2", shortName = "Maps Ex2")
@SpringBootApplication
public class MapsEx2Application implements AppShellConfigurator {

    @Autowired
    private Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(MapsEx2Application.class, args);
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

    @EventListener
    public void printApplicationUrl(final ApplicationStartedEvent event) {
        LoggerFactory.getLogger(MapsEx2Application.class).info("Application started at "
                + "http://localhost:"
                + environment.getProperty("local.server.port")
                + Strings.nullToEmpty(environment.getProperty("server.servlet.context-path")));
    }
    // tag::register-projection[]
    @Bean
    public ProjectionRegistration projectionRegistration21781() {
        return new GeoMapProjectionRegistration("EPSG:21781",
                "+proj=somerc +lat_0=46.9524055555556 " +
                        "+lon_0=7.43958333333333 " +
                        "+k_0=1 +x_0=600000 +y_0=200000 +ellps=bessel " +
                        "+towgs84=674.374,15.056,405.346,0,0,0,0 +units=m " +
                        "+no_defs +type=crs");
    }
    // end::register-projection[]
}
