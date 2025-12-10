package org.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "jobs")
public class Job {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(length = 2000)
    private String description;
    private Double payRate;
    private String category;

    public Job() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String d) { this.description = d; }
    public Double getPayRate() { return payRate; }
    public void setPayRate(Double p) { this.payRate = p; }
    public String getCategory() { return category; }
    public void setCategory(String c) { this.category = c; }
}