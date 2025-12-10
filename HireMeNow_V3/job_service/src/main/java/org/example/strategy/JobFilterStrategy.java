package org.example.strategy;

import org.example.model.Job;
import java.util.List;

@FunctionalInterface
public interface JobFilterStrategy {
    List<Job> filter(List<Job> all);
}