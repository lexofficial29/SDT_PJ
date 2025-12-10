package org.example.models;

public abstract class User {
    protected int id;
    protected String name;
    protected String email;
    protected String password;

    public User() {}
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public abstract void displayRole();

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getRole() {
        if (this instanceof Student) return "Student";
        if (this instanceof Employer) return "Employer";
        if (this instanceof Admin) return "Admin";
        return "Unknown";
    }
}
