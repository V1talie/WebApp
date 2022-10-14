package com.example.webapp.Controller;

import com.example.webapp.Model.Department;
import com.example.webapp.Service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class DepartmentController {

        @Autowired
        DepartmentService departmentService;

        @GetMapping("/departments")
        public ResponseEntity<List<Department>> getDepartments() {

            return departmentService.getAll();
        }

        @GetMapping("/departments/{id}")
        public ResponseEntity<Department> getDepartmentById(@PathVariable long id) {

            return departmentService.getById(id);
        }

        @PostMapping("/departments")
        public ResponseEntity<Department> addDepartment(@RequestBody Department department) {
            return departmentService.add(department);
        }

        @PutMapping("/departments/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable long id, @RequestBody Department department){
            return departmentService.updateDepartment(id, department);
        }
    }