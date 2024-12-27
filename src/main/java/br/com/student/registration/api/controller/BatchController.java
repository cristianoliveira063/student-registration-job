package br.com.student.registration.api.controller;

import br.com.student.registration.domain.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/batch")
@RequiredArgsConstructor
public class BatchController {

    private final JobService jobService;

    @GetMapping("/start")
    public ResponseEntity<String> runBatch() {
        return handleBatchOperation(jobService::startJob, "Batch Job Started Successfully");
    }

    @GetMapping("/stop/{jobExecutionId}")
    public ResponseEntity<String> stopBatch(@PathVariable long jobExecutionId) {
        return handleBatchOperation(() -> jobService.stopJob(jobExecutionId),
                "Batch Job Stopped Successfully");
    }

    @GetMapping("/abandon/{jobExecutionId}")
    public ResponseEntity<String> abandonBatch(@PathVariable long jobExecutionId) {
        return handleBatchOperation(() -> jobService.abandonJob(jobExecutionId),
                "Batch Job Abandoned Successfully");
    }

    private ResponseEntity<String> handleBatchOperation(Runnable operation, String successMessage) {
        try {
            operation.run();
            return ResponseEntity.ok(successMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
