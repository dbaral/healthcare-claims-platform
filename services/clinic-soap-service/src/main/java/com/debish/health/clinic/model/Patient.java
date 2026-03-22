package com.debish.health.clinic.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String firstName;

    @Column(nullable = false, length = 80)
    private String lastName;

    @Column(nullable = false, unique = true, length = 30)
    private String mrn; // Medical Record Number

    private LocalDate dateOfBirth;

    @Column(length = 15)
    private String phone;

    protected Patient() {
        // required by JPA
    }

    public Patient(String firstName, String lastName, String mrn, LocalDate dateOfBirth, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mrn = mrn;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
    }

    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getMrn() { return mrn; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }
}