package com.example.webapp.Service;

import com.example.webapp.Exception.DepartmentNotFoundException;
import com.example.webapp.Repository.DepartmentRepository;
import com.example.webapp.dto.DepartmentDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.webapp.util.DEPARTMENT_FOR_EDIT;
import static com.example.webapp.util.DEPARTMENT_LIST;
import static com.example.webapp.util.DEPARTMENT_LIST_DTO;
import static com.example.webapp.util.NEW_DEPARTMENT;
import static com.example.webapp.util.NEW_DEPARTMENT_DTO;
import static com.example.webapp.util.VALID_DEPARTMENT;
import static com.example.webapp.util.VALID_DEPARTMENT_DTO;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {
    @Mock
    DepartmentRepository departmentRepository;

    @InjectMocks
    DepartmentService departmentService;

    @Test
    void getAllDepartments_shouldReturnAllDepartments() {
        when(departmentRepository.findAll()).thenReturn(DEPARTMENT_LIST);
        List<DepartmentDTO> expectedList = departmentService.getAll();
        assertThat(expectedList).isEqualTo(DEPARTMENT_LIST_DTO);
        verify(departmentRepository).findAll();
    }

    @Test
    void getAllDepartmentsFromEmptyTable_shouldReturnAnEmptyList() {
        when(departmentRepository.findAll()).thenReturn(Collections.emptyList());
        List<DepartmentDTO> expectedList = departmentService.getAll();
        assertThat(expectedList).isEqualTo(Collections.emptyList());
        verify(departmentRepository).findAll();
    }

    @Test
    void getAnDepartmentByID_shouldReturnTheDepartment() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(VALID_DEPARTMENT));
        DepartmentDTO expectedDepartment = departmentService.getById(1L);
        assertThat(expectedDepartment).isEqualTo(VALID_DEPARTMENT_DTO);
        verify(departmentRepository).findById(1L);
    }

    @Test
    void getAnDepartmentByWrongID_shouldThrowAnException() {
        when(departmentRepository.findById(5L)).thenReturn(Optional.empty());
        final DepartmentNotFoundException departmentNotFoundException = new DepartmentNotFoundException("Department with id " + 5L + "not found");
        assertAll(
                () -> assertThrows(departmentNotFoundException.getClass(), () -> departmentService.getById(5L)),
                () -> assertEquals("Department with id " + 5L + "not found", departmentNotFoundException.getMessage())
        );
        verify(departmentRepository).findById(5L);
    }

    @Test
    void addNewDepartmentWithValidBody_shouldReturnTheDepartment() {
        //when(departmentRepository.findById(1L)).thenReturn(Optional.of(VALID_DEPARTMENT));
        when(departmentRepository.save(any())).thenReturn(VALID_DEPARTMENT);
        DepartmentDTO expectedDepartment = departmentService.add(VALID_DEPARTMENT_DTO);
        assertThat(expectedDepartment).isEqualTo(VALID_DEPARTMENT_DTO);
        verify(departmentRepository).save(any());
    }

    @Test
    void updateAnExistingEmployee_shouldReturnNewEmployeeBody() {
        // when(employeeRepository.findById(1L)).thenReturn(Optional.of(VALID_EMPLOYEE));
        when(departmentRepository.findById(2L)).thenReturn(Optional.of(DEPARTMENT_FOR_EDIT));
        when(departmentRepository.save(DEPARTMENT_FOR_EDIT)).thenReturn(NEW_DEPARTMENT);

        DepartmentDTO departmentDTO = departmentService.updateDepartment(2L, NEW_DEPARTMENT_DTO);
        verify(departmentRepository).findById(2L);

        assertAll(
                () -> assertThat(NEW_DEPARTMENT.getName()).isEqualTo(departmentDTO.getName()),
                () -> assertThat(NEW_DEPARTMENT.getLocation()).isEqualTo(departmentDTO.getLocation())

        );
    }
}
