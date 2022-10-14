package com.example.webapp.Controller;

import com.example.webapp.Model.Employee;
import com.example.webapp.Repository.EmployeeRepository;
import com.example.webapp.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class EmployeeController {

@Autowired
EmployeeService employeeService;

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getEmployees() {
       return employeeService.getAll();
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable long id) {
        return employeeService.getById(id);
    }

    @PostMapping("/employees")
    public ResponseEntity<Employee> addEmp(@RequestBody Employee employee){
      return employeeService.add(employee);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmp(@PathVariable long id, @RequestBody Employee employee){
        return employeeService.updateEmployee(id, employee);
    }
}
