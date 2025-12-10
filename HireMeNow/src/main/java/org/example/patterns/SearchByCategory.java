package org.example.patterns;

import org.example.models.Job;
import java.util.List;
import java.util.stream.Collectors;

public class SearchByCategory implements JobSearchStrategy {
    @Override
    public List<Job> search(List<Job> jobs, String category) {
        return jobs.stream()
                .filter(j -> j.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }
}

