package br.com.student.registration.job;

import br.com.student.registration.domain.model.Student;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {


    @Bean
    public FlatFileItemReader<StudentImport> reader() {
        return new FlatFileItemReaderBuilder<StudentImport>()
                .name("studentReader")
                .resource(new ClassPathResource("students_50k.csv")).strict(false)
                .linesToSkip(1)
                .delimited()
                .names("name", "email", "phone", "cpf", "age")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(StudentImport.class);
                }})
                .build();
    }

    @Bean
    public JpaItemWriter<Student> writer(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<Student> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        writer.setUsePersist(false);
        return writer;
    }

    @Bean
    public Job importAlunosJob(JobRepository jobRepository, Step partitionedStep) {
        return new JobBuilder("importStudentsJob", jobRepository)
                .listener(new JobCompletionNotificationListener())
                .start(partitionedStep)
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor();
        executor.setConcurrencyLimit(10);
        executor.setVirtualThreads(true);
        return executor;
    }

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(15);
        executor.setQueueCapacity(25);
        executor.setThreadNamePrefix("Batch-Job-");
        executor.initialize();
        return executor;
    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                      FlatFileItemReader<StudentImport> reader, ItemProcessor<StudentImport, Student> processor,
                      JpaItemWriter<Student> writer) {
        return new StepBuilder("step1", jobRepository)
                .<StudentImport, Student>chunk(1000, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .faultTolerant()
                .skip(Exception.class)
                .skipLimit(10)
                .listener(taskExecutor())
                .taskExecutor(threadPoolTaskExecutor())
                .build();
    }

    @Bean
    public Step partitionedStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                                Step multiThreadedStep) {
        return new StepBuilder("partitionedStep", jobRepository)
                .partitioner("step1", new FilePartitioner())
                .step(multiThreadedStep)
                .gridSize(5)
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public JobLauncher asyncJobLauncher(JobRepository jobRepository) throws Exception {
        TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.setTaskExecutor(threadPoolTaskExecutor());
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }


}
