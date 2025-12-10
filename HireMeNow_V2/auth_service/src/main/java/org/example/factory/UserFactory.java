package org.example.factory;

import org.example.model.Admin;
import org.example.model.Employer;
import org.example.model.Student;
import org.example.model.User;

public class UserFactory {

    public static User createUser(String role, String name, String email) {
        switch (role.toLowerCase()) {
            case "admin":
                return new Admin(name, email);
            case "employer":
                return new Employer(name, email);
            case "student":
                return new Student(name, email);
            default:
                throw new IllegalArgumentException("Unknown role: " + role);
        }
    }
}