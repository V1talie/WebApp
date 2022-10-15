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
    private long id;

    @Column(nullable = false)
    @NotBlank
    @NotEmpty
    private String first_name;

    @Column(nullable = false)
    @NotBlank
    @NotEmpty
    private String last_name;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Column(nullable = false, unique = true)
    @NotEmpty
    @NotBlank
    private String email;

    @Column(nullable = false, unique = true)
    private int phone_number;

    @Column(nullable = false)
    @Check(constraints = "salary >= 1.0")
    private double salary;

    public Employee(String first_name, String last_name, Department department, String email, int phone_number, double salary) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.department = department;
        this.email = email;
        this.phone_number = phone_number;
        this.salary = salary;
    }

}
