package org.example.strategy;

import org.example.model.Job;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryFilter implements JobFilterStrategy {
    private final String category;

    public CategoryFilter(String category) { this.category = category; }

    @Override
    public List<Job> filter(List<Job> all) {
        return all.stream()
                .filter(j -> category.equalsIgnoreCase(j.getCategory()))
                .collect(Collectors.toList());
    }
}