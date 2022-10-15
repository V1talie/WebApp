package com.example.webapp.Controller;

import com.example.webapp.Model.Employee;
import com.example.webapp.Service.EmployeeService;
import com.example.webapp.dto.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDTO>> getEmployees() {
        List<EmployeeDTO> list = employeeService.getAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable long id) {
        EmployeeDTO byId = employeeService.getById(id);
        return new ResponseEntity<>(byId, HttpStatus.OK);
    }

    @PostMapping("/employees")
    public ResponseEntity<EmployeeDTO> addEmp(@RequestBody EmployeeDTO employee) {
        employeeService.add(employee);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmp(@PathVariable long id, @RequestBody EmployeeDTO employee) {
        Employee update = employeeService.updateEmployee(id, employee);
        return new ResponseEntity<>(update, HttpStatus.OK);
    }
}
