package com.company.emailex1;

import com.company.emailex1.service.MyCustomEmailCleaningJob;
import com.company.emailex1.service.MyCustomEmailSendingJob;
import com.google.common.base.Strings;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import org.quartz.*;
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
@Theme(value = "email-ex1")
@PWA(name = "Email Ex1", shortName = "Email Ex1", offline = false)
@SpringBootApplication
public class EmailEx1Application implements AppShellConfigurator {

    @Autowired
    private Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(EmailEx1Application.class, args);
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
        LoggerFactory.getLogger(EmailEx1Application.class).info("Application started at "
                + "http://localhost:"
                + environment.getProperty("local.server.port")
                + Strings.nullToEmpty(environment.getProperty("server.servlet.context-path")));
    }

    // tag::quartz[]
    @Bean
    JobDetail myCustomEmailSendingJob() {
        return JobBuilder.newJob() // <1>
                .ofType(MyCustomEmailSendingJob.class)
                .storeDurably()
                .withIdentity("emailSending")
                .build(); // <2>
    }

    @Bean
    Trigger myCustomEmailSendingTrigger() {
        return TriggerBuilder.newTrigger() // <3>
                .forJob(myCustomEmailSendingJob()) // <4>
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("* 0/2 * * * ?"))
                .build(); // <5>
    }
    // end::quartz[]
    // tag::quartz-clean[]
    @Bean
    JobDetail myCustomEmailCleaningJob() {
        return JobBuilder.newJob() // <1>
                .ofType(MyCustomEmailCleaningJob.class)
                .storeDurably()
                .withIdentity("emailCleaning")
                .build(); // <2>
    }

    @Bean
    Trigger MyCustomEmailCleaningTrigger() {
        return TriggerBuilder.newTrigger() // <3>
                .forJob(myCustomEmailCleaningJob()) // <4>
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 0 1/2 * ? "))
                .build(); // <5>
    }
    // end::quartz-clean[]
}
