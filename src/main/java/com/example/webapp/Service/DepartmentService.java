package com.example.webapp.Service;

import com.example.webapp.Model.Department;
import com.example.webapp.Repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
@Autowired
DepartmentRepository departmentRepository;
    public ResponseEntity<List<Department>> getAll(){
        try{
            List<Department> departmentList = new ArrayList<>();
            departmentRepository.findAll().forEach(departmentList::add);
            if(departmentList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(departmentRepository.findAll(), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Department> getById(long id){
        Optional<Department> dep = departmentRepository.findById(id);
        if(dep.isPresent()){
            return new ResponseEntity<>(dep.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Department> add(Department department){
        try{
            Department department1 = departmentRepository.save(department);
            return new ResponseEntity<>(department1, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Department> updateDepartment(long id, Department department) {
        Optional<Department> departmentData = departmentRepository.findById(id);
    if(departmentData.isPresent()){
        Department newDep = departmentData.get();
        newDep.setName(department.getName());
        newDep.setLocation(department.getLocation());
        return new ResponseEntity<>(departmentRepository.save(newDep), HttpStatus.OK);
    } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    }
}
