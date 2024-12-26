package br.com.student.registration.domain.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class JobService {

    @Qualifier("asyncJobLauncher")
    private final JobLauncher asyncJobLauncher;
    private final Job job;

    public void startJob() {
        try {
            asyncJobLauncher.run(job, new JobParametersBuilder().addString("file", "500V2920").toJobParameters());
            log.info("Job finished");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
