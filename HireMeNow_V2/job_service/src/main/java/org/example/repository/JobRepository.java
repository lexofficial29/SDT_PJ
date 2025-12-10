package org.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.model.Job;

public interface JobRepository extends JpaRepository<Job, Long> {}
