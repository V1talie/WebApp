package com.example.webapp.Service;

import com.example.webapp.Exception.DepartmentNotFoundException;
import com.example.webapp.Model.Department;
import com.example.webapp.Repository.DepartmentRepository;
import com.example.webapp.dto.DepartmentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.webapp.Model.Department.convertDTOtoENT;
import static com.example.webapp.Model.Department.convertENTtoDTO;

@Service
@RequiredArgsConstructor
@Transactional
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public List<DepartmentDTO> getAll() {
        List<DepartmentDTO> departmentList = new ArrayList<>();
        departmentRepository.findAll().forEach(department -> departmentList.add(convertENTtoDTO(department)));
        if (CollectionUtils.isEmpty(departmentList)) {
            return Collections.emptyList();
        }
        return departmentList;
    }

    public DepartmentDTO getById(long id) {
        Optional<Department> departmentById = departmentRepository.findById(id);
        if (departmentById.isPresent()) {
            return convertENTtoDTO(departmentById.get());
        }
        throw new DepartmentNotFoundException("Department with id " + id + " not found");
    }

    public DepartmentDTO add(final DepartmentDTO department) {
        departmentRepository.save(convertDTOtoENT(department));
        return department;
    }

    public DepartmentDTO updateDepartment(long id, final DepartmentDTO department) {
        Optional<Department> departmentData = departmentRepository.findById(id);
        if (departmentData.isPresent()) {
            Department newDep = departmentData.get();
            newDep.setName(department.getName());
            newDep.setLocation(department.getLocation());
            departmentRepository.save(newDep);
            return convertENTtoDTO(newDep);
        } else {
            throw new DepartmentNotFoundException("Department with id " + id + "not found");
        }
    }

}
