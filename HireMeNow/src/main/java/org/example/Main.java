package org.example;

import org.example.models.*;
import org.example.patterns.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        DatabaseConnection db = DatabaseConnection.getInstance();
        if (db.getConnection() != null) {
            System.out.println("Connection test successful!");
        }

        User studentUser = UserFactory.createUser("student", "Alex", "alex@mail.com");
        User employerUser = UserFactory.createUser("employer", "TechCorp", "hr@techcorp.com");

        Student student = (Student) studentUser;
        Employer employer = (Employer) employerUser;

        student.displayRole();
        employer.displayRole();

        List<Job> jobs = new ArrayList<>();
        Job job1 = new Job("Frontend Intern", "IT", 800);
        Job job2 = new Job("Content Writer", "Marketing", 600);
        Job job3 = new Job("QA Tester", "IT", 900);

        jobs.add(job1);
        jobs.add(job2);
        jobs.add(job3);

        JobSearchStrategy strategy = new SearchByCategory();
        List<Job> itJobs = strategy.search(jobs, "IT");
        System.out.println("Jobs found in IT category: " + itJobs.size());

        strategy = new SearchByPayRate();
        List<Job> wellPaid = strategy.search(jobs, "700");
        System.out.println("Jobs with pay >= 700: " + wellPaid.size());

        JobApplicationFacade facade = new JobApplicationFacade();
        facade.apply(student, job1);
        facade.apply(student, job2);
        //facade.withdraw(student, job2);

        System.out.println("\nCheck your database: users, jobs, applications!");
    }
}