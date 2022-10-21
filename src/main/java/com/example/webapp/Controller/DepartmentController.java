package com.example.webapp.Controller;

import com.example.webapp.Service.DepartmentService;
import com.example.webapp.dto.DepartmentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/departments")
public class DepartmentController {


    private final DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getDepartments() {
        List<DepartmentDTO> departmentList = departmentService.getAll();
        if (CollectionUtils.isEmpty(departmentList)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(departmentList);
        }
        return ResponseEntity.ok(departmentList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable long id) {
        return ResponseEntity.ok(departmentService.getById(id));
    }

    @PostMapping
    public ResponseEntity<DepartmentDTO> addDepartment(@Valid @RequestBody DepartmentDTO department) {
        departmentService.add(department);
        return ResponseEntity.status(HttpStatus.CREATED).body(department);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDTO> updateDepartment(@PathVariable long id, @Valid @RequestBody DepartmentDTO department) {
        DepartmentDTO update = departmentService.updateDepartment(id, department);
        return ResponseEntity.status(HttpStatus.CREATED).body(update);

    }
}