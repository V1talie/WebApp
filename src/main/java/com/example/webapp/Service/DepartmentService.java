package com.example.webapp.Service;

import com.example.webapp.Model.Department;
import com.example.webapp.Repository.DepartmentRepository;
import com.example.webapp.dto.DepartmentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    public List<Department> getAll() {
        List<Department> departmentList = new ArrayList<>();
        departmentRepository.findAll().forEach(department -> departmentList.add(department));
        if (departmentList.isEmpty()) {
            return Collections.emptyList();
        }
        return departmentList;
    }

    public Department getById(long id) {
        Optional<Department> dep = departmentRepository.findById(id);
        return dep.orElse(null);
    }

    public DepartmentDTO add(DepartmentDTO department) {
        departmentRepository.save(convertDTOtoENT(department));
        return department;
    }

    public Department updateDepartment(long id, DepartmentDTO department) {
        Optional<Department> departmentData = departmentRepository.findById(id);
        if (departmentData.isPresent()) {
            Department newDep = departmentData.get();
            newDep.setName(department.getName());
            newDep.setLocation(department.getLocation());
            departmentRepository.save(newDep);
            return newDep;
        } else {
            return null;
        }
    }

    public Department convertDTOtoENT(DepartmentDTO departmentDTO) {
        return Department.builder()
                .department_id(departmentDTO.getId())
                .name(departmentDTO.getName())
                .location(departmentDTO.getLocation())
                .build();
    }

//    public DepartmentDTO convertENTtoDTO(Department department) {
//        return DepartmentDTO.builder()
//                .id(department.getDepartment_id())
//                .name(department.getName())
//                .location(department.getLocation())
//                .build();
//    }
}
