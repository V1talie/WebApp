package com.example.webapp.dto;

import com.example.webapp.Model.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTO {
    private Long id;

    @NotBlank(message = "Name can't be blank!")
    private String name;


    @NotBlank(message = "Location can't be blank!")
    private String location;
    private Set<Employee> employeeSet;

    public DepartmentDTO(Long id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }
}
