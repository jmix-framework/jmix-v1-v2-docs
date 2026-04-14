package com.company.demo.quartz;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;

// tag::job-params[]
public class SampleParameterizedJob implements Job {

    private static final Logger log = LoggerFactory.getLogger(SampleParameterizedJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        String paramStr = jobDataMap.entrySet().stream()
                .map(e -> e.getKey() + " : " + e.getValue())
                .collect(Collectors.joining(", ", "[", "]"));
        log.info("Sample job is executed with parameters: " + paramStr);
    }
}
// end::job-params[]
