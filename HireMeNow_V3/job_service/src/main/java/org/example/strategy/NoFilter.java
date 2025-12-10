package org.example.strategy;

import org.example.model.Job;
import java.util.List;

public class NoFilter implements JobFilterStrategy {
    @Override
    public List<Job> filter(List<Job> all) { return all; }
}