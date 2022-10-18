package com.example.webapp.Model;

import com.example.webapp.dto.DepartmentDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long departmentId;

    @Column(nullable = false)
    @NotBlank(message = "Name can't be blank!")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "Location can't be blank!")
    private String location;

    public Department(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public Department(Long departmentId, String name, String location) {
        this.departmentId = departmentId;
        this.name = name;
        this.location = location;
    }

    @JsonManagedReference
    @OneToMany(mappedBy = "department")
    private Set<Employee> employeeSet;

    public static Department convertDTOtoENT(final DepartmentDTO departmentDTO) {
        return Department.builder()
                .departmentId(departmentDTO.getId())
                .name(departmentDTO.getName())
                .location(departmentDTO.getLocation())
                .employeeSet(departmentDTO.getEmployeeSet())
                .build();
    }

    public static DepartmentDTO convertENTtoDTO(final Department department) {
        return DepartmentDTO.builder()
                .id(department.getDepartmentId())
                .name(department.getName())
                .location(department.getLocation())
                .employeeSet(department.getEmployeeSet())
                .build();
    }
}
