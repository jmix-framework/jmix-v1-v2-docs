package com.company.emailex1.service;

import io.jmix.email.Emailer;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

// tag::quartz[]
public class MyCustomEmailSendingJob implements Job {

    @Autowired
    private Emailer emailer;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        emailer.processQueuedEmails();
    }
}
// end::quartz[]