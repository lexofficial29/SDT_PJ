package org.example.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Employer")
public class Employer extends User {
    public Employer() {
        super();
    }

    public Employer(String name, String email) {
        super(name, email);
    }
}