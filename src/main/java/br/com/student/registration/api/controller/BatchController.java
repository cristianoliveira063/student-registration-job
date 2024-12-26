package br.com.student.registration.api.controller;

import br.com.student.registration.domain.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/batch")
@RequiredArgsConstructor
public class BatchController {

    private final JobService jobService;

    @GetMapping("/start")
    public ResponseEntity<String> runBatch() {
        try {
            jobService.startJob();
            return new ResponseEntity<>("Batch Job Started Successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
