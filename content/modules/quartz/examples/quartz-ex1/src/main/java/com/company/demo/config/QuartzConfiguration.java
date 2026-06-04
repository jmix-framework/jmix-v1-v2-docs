package com.company.demo.config;

// tag::quartz-configuration[]
import com.company.demo.quartz.SampleJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfiguration {

    @Bean
    JobDetail sampleJob() {
        return JobBuilder.newJob()
                .ofType(SampleJob.class) // <1>
                .storeDurably()
                .withIdentity("SampleJob") // <2>
                .build();
    }

    @Bean
    Trigger sampleJobTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(sampleJob()) // <3>
                .withIdentity("SampleJobTrigger") // <4>
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?")) // <5>
                .build();
    }
}
// end::quartz-configuration[]