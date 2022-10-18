package com.example.webapp.Service;

import com.example.webapp.Exception.EmployeeNotFoundException;
import com.example.webapp.Model.Department;
import com.example.webapp.Model.Employee;
import com.example.webapp.Repository.DepartmentRepository;
import com.example.webapp.Repository.EmployeeRepository;
import com.example.webapp.dto.EmployeeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;


    private final DepartmentRepository departmentRepository;

    public List<EmployeeDTO> getAll() {
        List<EmployeeDTO> employeeList = new ArrayList<>();
        employeeRepository.findAll().forEach(employee -> employeeList.add(convertENTtoDTO(employee)));
        if (CollectionUtils.isEmpty(employeeList)) {
            return Collections.emptyList();
        }
        return employeeList;
    }

    public EmployeeDTO getById(long id) {
        Optional<Employee> employeeById = employeeRepository.findById(id);
        if (employeeById.isPresent()) {
            return convertENTtoDTO(employeeById.get());
        }
        throw new EmployeeNotFoundException("Employee with id " + id + " not found");
    }


    public EmployeeDTO addEmployee(final EmployeeDTO employee) {
        Employee newEmployee = employeeRepository.save(convertDTOtoENT(employee));
        return convertENTtoDTO(newEmployee);
    }

    public EmployeeDTO updateEmployee(long id, final EmployeeDTO employeeDTO) {
        Department departmentOptional = departmentRepository.findById(employeeDTO.getDepartmentId())
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with id " + id + "not found"));
        Employee newEmp = employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee with id " + id + "not found"));
        newEmp.setFirstName(employeeDTO.getFirstName());
        newEmp.setLastName(employeeDTO.getLastName());
        newEmp.setDepartment(departmentOptional);
        newEmp.setEmail(employeeDTO.getEmail());
        newEmp.setPhoneNumber(employeeDTO.getPhoneNumber());
        newEmp.setSalary(employeeDTO.getSalary());
        employeeRepository.save(newEmp);
        return convertENTtoDTO(newEmp);
    }


    public Employee convertDTOtoENT(final EmployeeDTO employeeDTO) {
        Department departmentOptional = departmentRepository.findById(employeeDTO.getDepartmentId()).orElseThrow(() -> new RuntimeException("Not found by id"));
        return Employee.builder()
                .id(employeeDTO.getId())
                .firstName(employeeDTO.getFirstName())
                .lastName(employeeDTO.getLastName())
                .department(departmentOptional)
                .email(employeeDTO.getEmail())
                .phoneNumber(employeeDTO.getPhoneNumber())
                .salary(employeeDTO.getSalary())
                .build();
    }

    public EmployeeDTO convertENTtoDTO(final Employee employee) {
        return EmployeeDTO.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .departmentId(employee.getDepartment().getDepartmentId())
                .email(employee.getEmail())
                .phoneNumber(employee.getPhoneNumber())
                .salary(employee.getSalary())
                .build();
    }
}
