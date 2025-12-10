package org.example.patterns;

import org.example.models.Job;
import java.util.List;

public interface JobSearchStrategy {
    List<Job> search(List<Job> jobs, String keyword);
}

