package com.example.webapp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DepartmentDTO {
    private long id;
    private String name;
    private String location;
}
