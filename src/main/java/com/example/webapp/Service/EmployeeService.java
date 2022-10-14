package com.example.webapp.Service;

import com.example.webapp.Model.Employee;
import com.example.webapp.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
@Autowired
EmployeeRepository employeeRepository;
    public ResponseEntity<List<Employee>> getAll(){
        try{
            List<Employee> employeeList = new ArrayList<>();
            employeeRepository.findAll().forEach(employee -> employeeList.add(employee));
            if(employeeList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(employeeRepository.findAll(), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Employee> getById(long id){
        Optional<Employee> emp = employeeRepository.findById(id);
        if(emp.isPresent()){
            return new ResponseEntity<>(emp.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Employee> add(Employee employee){
        try{
            Employee employee1 = employeeRepository.save(employee);
            return new ResponseEntity<>(employee1, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Employee> updateEmployee(long id, Employee employee) {
        Optional<Employee> employeeData = employeeRepository.findById(id);
        if(employeeData.isPresent()){
            Employee newEmp = employeeData.get();
            newEmp.setFirst_name(employee.getFirst_name());
            newEmp.setLast_name(employee.getLast_name());
            newEmp.setDepartment(employee.getDepartment());
            newEmp.setEmail(employee.getEmail());
            newEmp.setPhone_number(employee.getPhone_number());
            newEmp.setSalary(employee.getSalary());
            return new ResponseEntity<>(employeeRepository.save(newEmp), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
