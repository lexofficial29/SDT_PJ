package org.example.patterns;

import org.example.models.Job;
import java.util.List;
import java.util.stream.Collectors;

public class SearchByPayRate implements JobSearchStrategy {
    @Override
    public List<Job> search(List<Job> jobs, String minPay) {
        double pay = Double.parseDouble(minPay);
        return jobs.stream()
                .filter(j -> j.getPayRate() >= pay)
                .collect(Collectors.toList());
    }
}

