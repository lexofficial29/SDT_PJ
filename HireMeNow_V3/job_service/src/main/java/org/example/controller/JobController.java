package org.example.controller;

import org.example.model.Job;
import org.example.service.JobFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobFacade facade;

    public JobController(JobFacade facade) { this.facade = facade; }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Job j) {
        return ResponseEntity.ok(facade.create(j));
    }

    @GetMapping
    public List<Job> all(@RequestParam(required = false) String category,
                         @RequestParam(required = false) Double minPay) {
        return facade.list(category, minPay);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> get(@PathVariable Long id) {
        return ResponseEntity.ok(facade.get(id));
    }

    @GetMapping("/")
    public String root() {
        return "Job service is running!";
    }
}