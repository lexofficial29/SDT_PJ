package org.example.service;

import org.example.model.ApplicationEntity;
import org.example.repository.ApplicationRepository;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;

public class RestValidation extends ValidationTemplate {

    private final RestTemplate rest;
    private final ApplicationRepository repo;
    private final String authUrl;
    private final String jobUrl;

    public RestValidation(RestTemplate rest, ApplicationRepository repo,
                          String authUrl, String jobUrl) {
        this.rest = rest; this.repo = repo; this.authUrl = authUrl; this.jobUrl = jobUrl;
    }

    @Override protected boolean validateStudent(Long id) { return exists(authUrl + "/api/auth/users/" + id); }
    @Override protected boolean validateJob(Long id)    { return exists(jobUrl + "/api/jobs/" + id); }

    @Override
    protected ResponseEntity<?> doPersist(Long studentId, Long jobId) {
        ApplicationEntity a = new ApplicationEntity();
        a.setStudentId(studentId); a.setJobId(jobId);
        a.setStatus("SUBMITTED"); a.setAppliedAt(LocalDateTime.now());
        return ResponseEntity.ok(repo.save(a));
    }

    private boolean exists(String url) {
        try { return rest.getForEntity(url, String.class).getStatusCode().is2xxSuccessful(); }
        catch (Exception e) { return false; }
    }
}