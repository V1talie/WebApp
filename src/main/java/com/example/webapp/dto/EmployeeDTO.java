package com.example.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
public class EmployeeDTO {

    private Long id;

    @NotBlank(message = "First name can't be blank!")
    private String firstName;

    @NotBlank(message = "Last name can't be blank!")
    private String lastName;

    @NotNull(message = "Department_id can't be null")
    private Long departmentId;

    @NotBlank(message = "Email can't be blank!")
    private String email;

    @NotNull(message = "Phone number can't be null")
    private Integer phoneNumber;

    @Min(1)
    private double salary;


}
