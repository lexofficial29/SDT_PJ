package org.example.models;

public class Student extends User {
    private String portfolio;

    public Student(String name, String email) {
        super(name, email);
    }

    @Override
    public void displayRole() {
        System.out.println("Role: Student");
    }

    public void applyForJob(Job job) {
        System.out.println(name + " applied for job: " + job.getTitle());
    }
}