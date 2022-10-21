package com.example.webapp.Exception;

public class EmployeeNotFoundException extends NotFoundException {
    public EmployeeNotFoundException(String message) {
        super(message);
    }
}