package org.example.models;

public class Application {
    private Student student;
    private Job job;
    private String status = "Pending";

    public Application(Student student, Job job) {
        this.student = student;
        this.job = job;
    }

    public void submit() {
        System.out.println("Application submitted by " + student.getName() +
                " for job " + job.getTitle());
        status = "Submitted";
    }

    public void updateStatus(String newStatus) {
        status = newStatus;
        System.out.println("Application status: " + status);
    }
}
