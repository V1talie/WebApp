package com.example.webapp.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long department_id;

    @Column(nullable = false)
    @NotBlank
    @NotEmpty
    private String name;

    @Column(nullable = false)
    @NotBlank
    @NotEmpty
    private String location;

    public Department(String name, String location) {
        this.name = name;
        this.location = location;
    }


}
