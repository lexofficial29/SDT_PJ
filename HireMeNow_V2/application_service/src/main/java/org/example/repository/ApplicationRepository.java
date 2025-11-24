package org.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.model.ApplicationEntity;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Long> {
    List<ApplicationEntity> findByStudentId(Long studentId);
    List<ApplicationEntity> findByJobId(Long jobId);
}