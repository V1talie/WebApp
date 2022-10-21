package com.example.webapp.Service;


import com.example.webapp.Exception.EmployeeNotFoundException;
import com.example.webapp.Repository.DepartmentRepository;
import com.example.webapp.Repository.EmployeeRepository;
import com.example.webapp.dto.EmployeeDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.webapp.util.EMPLOYEE_FOR_CHANGE;
import static com.example.webapp.util.EMPLOYEE_LIST;
import static com.example.webapp.util.EMPLOYEE_LIST_DTO;
import static com.example.webapp.util.NEW_EMPLOYEE;
import static com.example.webapp.util.NEW_EMPLOYEE_DTO;
import static com.example.webapp.util.VALID_DEPARTMENT;
import static com.example.webapp.util.VALID_EMPLOYEE;
import static com.example.webapp.util.VALID_EMPLOYEE_DTO;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {
    @Mock
    EmployeeRepository employeeRepository;
    @Mock
    DepartmentRepository departmentRepository;
    @InjectMocks
    EmployeeService employeeService;

    @Test
    void getAllEmployees_shouldReturnAllEmployees() {
        when(employeeRepository.findAll()).thenReturn(EMPLOYEE_LIST);
        List<EmployeeDTO> expectedList = employeeService.getAll();
        assertThat(expectedList).isEqualTo(EMPLOYEE_LIST_DTO);
        verify(employeeRepository).findAll();
    }

    @Test
    void getAllEmployeesFromEmptyTable_shouldReturnAnEmptyList() {
        when(employeeRepository.findAll()).thenReturn(Collections.emptyList());
        List<EmployeeDTO> expectedList = employeeService.getAll();
        assertThat(expectedList).isEqualTo(Collections.emptyList());
        verify(employeeRepository).findAll();
    }

    @Test
    void getAnEmployeeByID_shouldReturnTheEmployee() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(VALID_EMPLOYEE));
        EmployeeDTO expectedEmployee = employeeService.getById(1L);
        assertThat(expectedEmployee).isEqualTo(VALID_EMPLOYEE_DTO);
        verify(employeeRepository).findById(1L);
    }

    @Test
    void getAnEmployeeByWrongID_shouldThrowAnException() {
        when(employeeRepository.findById(5L)).thenReturn(Optional.empty());
        final EmployeeNotFoundException employeeNotFoundException = new EmployeeNotFoundException("Employee with id " + 5L + "not found");
        assertAll(
                () -> assertThrows(employeeNotFoundException.getClass(), () -> employeeService.getById(5L)),
                () -> assertEquals("Employee with id " + 5L + "not found", employeeNotFoundException.getMessage())
        );
        verify(employeeRepository).findById(5L);
    }

    @Test
    void addNewEmployeeWithValidBody_shouldReturnTheEmployee() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(VALID_DEPARTMENT));
        when(employeeRepository.save(any())).thenReturn(VALID_EMPLOYEE);
        EmployeeDTO expectedEmployee = employeeService.addEmployee(VALID_EMPLOYEE_DTO);
        assertThat(expectedEmployee).isEqualTo(VALID_EMPLOYEE_DTO);
        verify(employeeRepository).save(any());
    }

    @Test
    void updateAnExistingEmployee_shouldReturnNewEmployeeBody() {
        when(employeeRepository.findById(5L)).thenReturn(Optional.of(EMPLOYEE_FOR_CHANGE));
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(VALID_DEPARTMENT));
        when(employeeRepository.save(EMPLOYEE_FOR_CHANGE)).thenReturn(NEW_EMPLOYEE);

        EmployeeDTO employeeDTO = employeeService.updateEmployee(5L, NEW_EMPLOYEE_DTO);
        verify(employeeRepository).findById(5L);

        assertAll(
                () -> assertThat(NEW_EMPLOYEE.getFirstName()).isEqualTo(employeeDTO.getFirstName()),
                () -> assertThat(NEW_EMPLOYEE.getLastName()).isEqualTo(employeeDTO.getLastName()),
                () -> assertThat(NEW_EMPLOYEE.getDepartment().getDepartmentId()).isEqualTo(employeeDTO.getDepartmentId()),
                () -> assertThat(NEW_EMPLOYEE.getEmail()).isEqualTo(employeeDTO.getEmail()),
                () -> assertThat(NEW_EMPLOYEE.getSalary()).isEqualTo(employeeDTO.getSalary())
        );
    }
}
