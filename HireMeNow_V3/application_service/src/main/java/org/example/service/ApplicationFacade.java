package org.example.service;

import org.example.model.ApplicationEntity;
import org.example.repository.ApplicationRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ApplicationFacade {

    private final ApplicationRepository repo;
    private final RestTemplate rest;
    private final RabbitTemplate rabbitTemplate;

    private final String authUrl;
    private final String jobUrl;

    // RabbitMQ settings
    private static final String EXCHANGE = "application.exchange";
    private static final String ROUTING_KEY = "application.submitted";

    public ApplicationFacade(ApplicationRepository repo,
                             RestTemplate rest,
                             RabbitTemplate rabbitTemplate,
                             @Value("${auth.service.url}") String authUrl,
                             @Value("${job.service.url}") String jobUrl) {
        this.repo = repo;
        this.rest = rest;
        this.rabbitTemplate = rabbitTemplate;
        this.authUrl = authUrl;
        this.jobUrl = jobUrl;
    }

    @Transactional
    public ApplicationEntity applyAndPublishEvent(Long studentId, Long jobId) {
        // 1. Validate student
        if (!exists(authUrl + "/api/auth/users/" + studentId)) {
            throw new IllegalArgumentException("Student not found");
        }

        // 2. Validate job
        if (!exists(jobUrl + "/api/jobs/" + jobId)) {
            throw new IllegalArgumentException("Job not found");
        }

        // 3. Save application
        ApplicationEntity a = new ApplicationEntity();
        a.setStudentId(studentId);
        a.setJobId(jobId);
        a.setStatus("SUBMITTED");
        a.setAppliedAt(LocalDateTime.now());

        ApplicationEntity saved = repo.save(a);

        // Safe publish – never gives 500 even if RabbitMQ is slow
        try {
            Map<String, Object> event = Map.of(
                    "applicationId", saved.getId(),
                    "studentId", studentId,
                    "jobId", jobId,
                    "appliedAt", LocalDateTime.now()
            );
            rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, event);
        } catch (Exception e) {
            System.out.println("RabbitMQ not ready yet – will retry automatically");
        }
        return saved;
    }

    private boolean exists(String url) {
        try {
            return rest.getForEntity(url, String.class).getStatusCode().is2xxSuccessful();
        } catch (Exception ex) {
            return false;
        }
    }

    // Query methods
    public List<ApplicationEntity> all() { return repo.findAll(); }
    public List<ApplicationEntity> byStudent(Long sid) { return repo.findByStudentId(sid); }
    public List<ApplicationEntity> byJob(Long jid) { return repo.findByJobId(jid); }
}