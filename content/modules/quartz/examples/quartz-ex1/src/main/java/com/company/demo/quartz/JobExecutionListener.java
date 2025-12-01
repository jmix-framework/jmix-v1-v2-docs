package com.company.demo.quartz;

import jakarta.annotation.PostConstruct;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.listeners.JobListenerSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobExecutionListener extends JobListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(JobExecutionListener.class);

    @Autowired
    private Scheduler scheduler;

    @Override
    public String getName() {
        return "SampleJobExecutionListener";
    }

    @PostConstruct
    private void registerListener() { // <1>
        try {
            scheduler.getListenerManager().addJobListener(this);
        } catch (SchedulerException e) {
            log.error("Cannot register job listener", e);
        }
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context,
                               JobExecutionException jobException) { // <2>
        log.info("jobWasExecuted: name={}, context={}",
                context.getJobDetail().getKey().getName(), context);
    }
}
