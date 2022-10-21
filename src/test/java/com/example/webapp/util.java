package com.example.webapp;

import com.example.webapp.Model.Department;
import com.example.webapp.Model.Employee;
import com.example.webapp.dto.DepartmentDTO;
import com.example.webapp.dto.EmployeeDTO;

import java.util.Arrays;
import java.util.List;

public class util {

    public static final Department VALID_DEPARTMENT = new Department(1L, "IT", "Chisinau");

    public static final Department DEPARTMENT_FOR_EDIT = new Department(2L, "IT", "Chisinau");


    public static final Department NEW_DEPARTMENT = new Department(3L, "HR", "Iasi");

    public static final EmployeeDTO VALID_EMPLOYEE_DTO = new EmployeeDTO(3L, "testName", "testLast", 1L, "testEmail", 1234, 3.0);

    public static final EmployeeDTO SUPER_VALID_EMPLOYEE_DTO = new EmployeeDTO(1L, "superName", "superLast", 1L, "superEmail", 7777, 999.0);

    public static final EmployeeDTO INVALID_EMPLOYEE_DTO = new EmployeeDTO(20L, "testName", "a", 1L, "a", 1234, 0.4);

    public static final EmployeeDTO NEW_EMPLOYEE_DTO = new EmployeeDTO(1L, "newName", "newLast", 1L, "newEmail", 12345, 5.0);

    public static final Employee VALID_EMPLOYEE = new Employee(3L, "testName", "testLast", VALID_DEPARTMENT, "testEmail", 1234, 3.0);

    public static final Employee SUPER_VALID_EMPLOYEE = new Employee(1L, "superName", "superLast", VALID_DEPARTMENT, "superEmail", 7777, 999.0);

    public static final Employee EMPLOYEE_FOR_CHANGE = new Employee(5L, "testName", "testLast", VALID_DEPARTMENT, "testEmail", 1234, 3.0);

    public static final Employee NEW_EMPLOYEE = new Employee(1L, "newName", "newLast", VALID_DEPARTMENT, "newEmail", 12345, 5.0);

    public static final DepartmentDTO VALID_DEPARTMENT_DTO = new DepartmentDTO(1L, "IT", "Chisinau");

    public static final DepartmentDTO INVALID_DEPARTMENT_DTO = new DepartmentDTO(20L, "", "Da");

    public static final DepartmentDTO NEW_DEPARTMENT_DTO = new DepartmentDTO(1L, "HR", "Iasi");


    public static final List<DepartmentDTO> DEPARTMENT_LIST_DTO = Arrays.asList(
            new DepartmentDTO(1L, "IT", "Chisinau"),
            new DepartmentDTO(2L, "HR", "Iasi")
    );
    public static final List<DepartmentDTO> REVERSE_DEPARTMENT_LIST_DTO = Arrays.asList(
            new DepartmentDTO(2L, "HR", "Iasi"),
            new DepartmentDTO(1L, "IT", "Chisinau")

    );

    public static final List<Department> DEPARTMENT_LIST = Arrays.asList(
            new Department(1L, "IT", "Chisinau"),
            new Department(2L, "HR", "Iasi")
    );
    public static final List<EmployeeDTO> EMPLOYEE_LIST_DTO = Arrays.asList(
            new EmployeeDTO(1L, "unu", "lUnu", 1L, "email1", 12356789, 2.0),
            new EmployeeDTO(2L, "doi", "lDoi", 1L, "email2", 12498765, 3.0)
    );

    public static final List<Employee> EMPLOYEE_LIST = Arrays.asList(
            new Employee(1L, "unu", "lUnu", VALID_DEPARTMENT, "email1", 12356789, 2.0),
            new Employee(2L, "doi", "lDoi", VALID_DEPARTMENT, "email2", 12498765, 3.0)
    );

}
