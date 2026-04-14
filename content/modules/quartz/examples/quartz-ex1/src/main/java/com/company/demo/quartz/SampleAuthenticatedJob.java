package com.company.demo.quartz;

import com.company.demo.entity.User;
import io.jmix.core.DataManager;
import io.jmix.core.security.Authenticated;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

// tag::auth-job[]
public class SampleAuthenticatedJob implements Job {

    private static final Logger log = LoggerFactory.getLogger(SampleAuthenticatedJob.class);

    @Autowired
    private DataManager dataManager;

    @Authenticated
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        int usersCount = dataManager.load(User.class).all().list().size();
        log.info("There are {} registered users", usersCount);
    }
}
// end::auth-job[]
