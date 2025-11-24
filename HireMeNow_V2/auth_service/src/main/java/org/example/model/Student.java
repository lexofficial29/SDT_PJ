package org.example.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Student")
public class Student extends User {
    public Student() {
        super();
    }

    public Student(String name, String email) {
        super(name, email);
    }
}