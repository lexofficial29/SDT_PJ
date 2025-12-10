package org.example.strategy;

import org.example.model.Job;
import java.util.List;
import java.util.stream.Collectors;

public class PayRateFilter implements JobFilterStrategy {
    private final Double minPay;

    public PayRateFilter(Double minPay) { this.minPay = minPay; }

    @Override
    public List<Job> filter(List<Job> all) {
        return all.stream()
                .filter(j -> j.getPayRate() != null && j.getPayRate() >= minPay)
                .collect(Collectors.toList());
    }
}