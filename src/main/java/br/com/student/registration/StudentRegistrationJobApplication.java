package br.com.student.registration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;

@SpringBootApplication
@EnableTask
public class StudentRegistrationJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentRegistrationJobApplication.class, args);
    }

}
