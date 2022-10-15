package com.example.webapp.Service;

import com.example.webapp.Model.Department;
import com.example.webapp.Model.Employee;
import com.example.webapp.Repository.DepartmentRepository;
import com.example.webapp.Repository.EmployeeRepository;
import com.example.webapp.dto.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    public List<EmployeeDTO> getAll() {
        List<EmployeeDTO> employeeList = new ArrayList<>();
        employeeRepository.findAll().forEach(employee -> employeeList.add(convertENTtoDTO(employee)));
        if (employeeList.isEmpty()) {
            return Collections.emptyList();
        }
        return employeeList;
    }

    public EmployeeDTO getById(long id) {
        Optional<Employee> emp = employeeRepository.findById(id);
        if(emp.isPresent()) {
            return convertENTtoDTO(emp.get());
        }
        return null;
    }


    public EmployeeDTO add(EmployeeDTO employee) {
        employeeRepository.save(convertDTOtoENT(employee));
        return employee;
    }

    public Employee updateEmployee(long id, EmployeeDTO employeeDTO) {
        Optional<Employee> employeeData = employeeRepository.findById(id);
        if (employeeData.isPresent()) {
            Department departmentOptional = departmentRepository.findById(employeeDTO.getDepartment_id()).orElseThrow(() -> new RuntimeException("Not found by id"));
            Employee newEmp = employeeData.get();
            newEmp.setFirst_name(employeeDTO.getFirst_name());
            newEmp.setLast_name(employeeDTO.getLast_name());
            newEmp.setDepartment(departmentOptional);
            newEmp.setEmail(employeeDTO.getEmail());
            newEmp.setPhone_number(employeeDTO.getPhone_number());
            newEmp.setSalary(employeeDTO.getSalary());
            employeeRepository.save(newEmp);
            return newEmp;
        } else {
            return null;
        }
    }

    public Employee convertDTOtoENT(EmployeeDTO employeeDTO) {
        Department departmentOptional = departmentRepository.findById(employeeDTO.getDepartment_id()).orElseThrow(() -> new RuntimeException("Not found by id"));
        return Employee.builder()
                .id(employeeDTO.getId())
                .first_name(employeeDTO.getFirst_name())
                .last_name(employeeDTO.getLast_name())
                .department(departmentOptional)
                .email(employeeDTO.getEmail())
                .phone_number(employeeDTO.getPhone_number())
                .salary(employeeDTO.getSalary())
                .build();
    }

    public EmployeeDTO convertENTtoDTO(Employee employee) {
        return EmployeeDTO.builder()
                .id(employee.getId())
                .first_name(employee.getFirst_name())
                .last_name(employee.getLast_name())
                .department_id(employee.getDepartment().getDepartment_id())
                .email(employee.getEmail())
                .phone_number(employee.getPhone_number())
                .salary(employee.getSalary())
                .build();
    }
}
