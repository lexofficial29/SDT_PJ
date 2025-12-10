package org.example.controller;

import org.example.model.ApplicationEntity;
import org.example.service.ApplicationFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationFacade facade;

    public ApplicationController(ApplicationFacade facade) {
        this.facade = facade;
    }

    @PostMapping
    public ResponseEntity<?> apply(@RequestBody Map<String, Object> payload) {
        try {
            Long studentId = ((Number) payload.get("studentId")).longValue();
            Long jobId = ((Number) payload.get("jobId")).longValue();

            ApplicationEntity saved = facade.applyAndPublishEvent(studentId, jobId);

            return ResponseEntity.accepted().body(Map.of(
                    "message", "Application submitted successfully – processing in background",
                    "applicationId", saved.getId(),
                    "status", "202 Accepted – async processing started"
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "error", "Internal server error"
            ));
        }
    }

    @GetMapping public List<ApplicationEntity> all() { return facade.all(); }
    @GetMapping("/by-student/{sid}") public List<ApplicationEntity> byStudent(@PathVariable Long sid) { return facade.byStudent(sid); }
    @GetMapping("/by-job/{jid}")     public List<ApplicationEntity> byJob(@PathVariable Long jid)     { return facade.byJob(jid); }

    @GetMapping("/")
    public String root() {
        return "Application service is running!";
    }
}