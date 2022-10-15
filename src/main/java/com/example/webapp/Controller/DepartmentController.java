package com.example.webapp.Controller;

import com.example.webapp.Model.Department;
import com.example.webapp.Service.DepartmentService;
import com.example.webapp.dto.DepartmentDTO;
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
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @GetMapping("/departments")
    public ResponseEntity<List<Department>> getDepartments() {
        List<Department> list = departmentService.getAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/departments/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable long id) {
        Department byId = departmentService.getById(id);
        return new ResponseEntity<>(byId, HttpStatus.OK);
    }

    @PostMapping("/departments")
    public ResponseEntity<DepartmentDTO> addDepartment(@RequestBody DepartmentDTO department) {
        departmentService.add(department);
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    @PutMapping("/departments/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable long id, @RequestBody DepartmentDTO department) {
        Department update = departmentService.updateDepartment(id, department);
        return new ResponseEntity<>(update, HttpStatus.OK);
    }
}