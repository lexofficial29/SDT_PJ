package org.example.service;

import org.example.model.ApplicationEntity;
import org.example.repository.ApplicationRepository;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApplicationFacade {

    private final ApplicationRepository repo;
    private final RestTemplate rest;
    private final String authUrl;
    private final String jobUrl;

    public ApplicationFacade(ApplicationRepository repo, RestTemplate rest,
                             @org.springframework.beans.factory.annotation.Value("${auth.service.url}") String authUrl,
                             @org.springframework.beans.factory.annotation.Value("${job.service.url}") String jobUrl) {
        this.repo = repo;
        this.rest = rest;
        this.authUrl = authUrl;
        this.jobUrl = jobUrl;
    }

    /** Public façade method – the only one the controller calls */
    public ResponseEntity<?> apply(Long studentId, Long jobId) {
        // 1. validate student
        if (!exists(authUrl + "/api/auth/users/" + studentId))
            return ResponseEntity.badRequest().body("Student not found");

        // 2. validate job
        if (!exists(jobUrl + "/api/jobs/" + jobId))
            return ResponseEntity.badRequest().body("Job not found");

        // 3. persist
        ApplicationEntity a = new ApplicationEntity();
        a.setStudentId(studentId);
        a.setJobId(jobId);
        a.setStatus("SUBMITTED");
        a.setAppliedAt(LocalDateTime.now());

        return ResponseEntity.ok(repo.save(a));
    }

    private boolean exists(String url) {
        try {
            return rest.getForEntity(url, String.class).getStatusCode().is2xxSuccessful();
        } catch (Exception ex) {
            return false;
        }
    }

    public List<ApplicationEntity> all() { return repo.findAll(); }
    public List<ApplicationEntity> byStudent(Long sid) { return repo.findByStudentId(sid); }
    public List<ApplicationEntity> byJob(Long jid) { return repo.findByJobId(jid); }
}