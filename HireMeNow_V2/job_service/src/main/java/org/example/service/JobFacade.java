package org.example.service;

import org.example.model.Job;
import org.example.repository.JobRepository;
import org.example.strategy.JobFilterStrategy;
import org.example.strategy.NoFilter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobFacade {

    private final JobRepository repo;
    private JobFilterStrategy filter = new NoFilter();

    public JobFacade(JobRepository repo) { this.repo = repo; }

    // Facade entry point – create
    public Job create(Job job) {
        if (job.getTitle() == null) throw new IllegalArgumentException("Missing title");
        return repo.save(job);
    }

    // Facade entry point – list with optional strategy
    public List<Job> list(String category, Double minPay) {
        List<Job> all = repo.findAll();

        if (category != null) {
            filter = new org.example.strategy.CategoryFilter(category);
        } else if (minPay != null) {
            filter = new org.example.strategy.PayRateFilter(minPay);
        } else {
            filter = new NoFilter();
        }
        return filter.filter(all);
    }

    // Facade entry point – get by id
    public Job get(Long id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Job not found"));
    }
}