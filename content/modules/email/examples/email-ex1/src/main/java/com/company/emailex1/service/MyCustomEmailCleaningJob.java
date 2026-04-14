package com.company.emailex1.service;
// tag::quartz[]
import io.jmix.email.EmailCleaner;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

public class MyCustomEmailCleaningJob implements Job {

    @Autowired
    private EmailCleaner emailCleaner;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        emailCleaner.deleteOldEmails();
    }
}
// end::quartz[]
