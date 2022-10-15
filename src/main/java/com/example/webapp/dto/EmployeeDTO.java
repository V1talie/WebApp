package com.example.webapp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmployeeDTO {
    private long id;
    private String first_name;
    private String last_name;
    private String email;
    private int phone_number;
    private double salary;
    private long department_id;
}
