package org.example.models;

public class Employer extends User {

    public Employer(String name, String email) {
        super(name, email);
    }

    @Override
    public void displayRole() {
        System.out.println("Role: Employer");
    }

    public void postJob(Job job) {
        System.out.println(name + " posted a new job: " + job.getTitle());
    }
}

