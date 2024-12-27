package br.com.student.registration.domain.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class JobService {

    @Qualifier("asyncJobLauncher")
    private final JobLauncher asyncJobLauncher;
    private final JobOperator jobOperator;
    private final Job job;

    public void startJob() {
        try {
            asyncJobLauncher.run(job, new JobParametersBuilder().addString("file", "500V333").toJobParameters());
            log.info("Job finished");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void stopJob(long jobExecutionId) {
        try {
            jobOperator.stop(jobExecutionId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void abandonJob(long jobExecutionId) {
        try {
            jobOperator.abandon(jobExecutionId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
