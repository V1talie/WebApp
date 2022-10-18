package com.example.webapp.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "First name can't be blank!")
    @NotEmpty(message = "First name can't be empty!")
    private String firstName;

    @Column(nullable = false)
    @NotBlank(message = "Last name can't be blank!")
    private String lastName;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departmentId", nullable = false)
    private Department department;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Email can't be blank!")
    private String email;

    @Column(nullable = false, unique = true)
    private Integer phoneNumber;

    @Column(nullable = false)
    @Check(constraints = "salary >= 1.0")
    private double salary;

    public Employee(String firstName, String lastName, Department department, String email, int phoneNumber, double salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.salary = salary;
    }

}
