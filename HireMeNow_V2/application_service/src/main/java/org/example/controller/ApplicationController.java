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

    public ApplicationController(ApplicationFacade facade) { this.facade = facade; }

    @PostMapping
    public ResponseEntity<?> apply(@RequestBody Map<String, Object> payload) {
        Long studentId = ((Number) payload.get("studentId")).longValue();
        Long jobId    = ((Number) payload.get("jobId")).longValue();
        return facade.apply(studentId, jobId);
    }

    @GetMapping public List<ApplicationEntity> all() { return facade.all(); }
    @GetMapping("/by-student/{sid}") public List<ApplicationEntity> byStudent(@PathVariable Long sid) { return facade.byStudent(sid); }
    @GetMapping("/by-job/{jid}")     public List<ApplicationEntity> byJob(@PathVariable Long jid)     { return facade.byJob(jid); }

    @GetMapping("/")
    public String root() {
        return "Application service is running!";
    }
}