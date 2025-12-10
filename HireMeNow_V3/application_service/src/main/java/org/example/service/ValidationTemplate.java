package org.example.service;

import org.springframework.http.ResponseEntity;

public abstract class ValidationTemplate {

    public final ResponseEntity<?> execute(Long studentId, Long jobId) {
        if (!validateStudent(studentId)) return ResponseEntity.badRequest().body("Student not found");
        if (!validateJob(jobId))       return ResponseEntity.badRequest().body("Job not found");
        return doPersist(studentId, jobId);
    }

    protected abstract boolean validateStudent(Long id);
    protected abstract boolean validateJob(Long id);
    protected abstract ResponseEntity<?> doPersist(Long studentId, Long jobId);
}