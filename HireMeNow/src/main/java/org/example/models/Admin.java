package org.example.models;

public class Admin extends User {

    public Admin(String name, String email) {
        super(name, email);
    }

    @Override
    public void displayRole() {
        System.out.println("Role: Administrator");
    }

    public void manageUsers() {
        System.out.println("Admin managing users...");
    }
}

