package com.example.webapp.Integration;

import com.example.webapp.Exception.EmployeeNotFoundException;
import com.example.webapp.Repository.DepartmentRepository;
import com.example.webapp.Repository.EmployeeRepository;
import com.example.webapp.Service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.example.webapp.util.EMPLOYEE_LIST;
import static com.example.webapp.util.EMPLOYEE_LIST_DTO;
import static com.example.webapp.util.INVALID_EMPLOYEE_DTO;
import static com.example.webapp.util.NEW_EMPLOYEE_DTO;
import static com.example.webapp.util.SUPER_VALID_EMPLOYEE;
import static com.example.webapp.util.SUPER_VALID_EMPLOYEE_DTO;
import static com.example.webapp.util.VALID_DEPARTMENT;
import static com.example.webapp.util.VALID_EMPLOYEE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
class EmployeeControllerIntegrationTest {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;


    @Autowired
    Flyway flyway;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmployeeService employeeService;

    @BeforeEach
    public void cleanUp() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    void getAll_shouldReturnAllEmployees() throws Exception {
        departmentRepository.save(VALID_DEPARTMENT);
        employeeRepository.save(EMPLOYEE_LIST.get(0));
        employeeRepository.save(EMPLOYEE_LIST.get(1));

        MvcResult mvcResult = mockMvc.perform(get("/employees")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();
        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertThat(actualResponse).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(EMPLOYEE_LIST_DTO));
    }

    @Test
    void getEmployeeById_shouldReturnTheEmployees() throws Exception {
        departmentRepository.save(VALID_DEPARTMENT);
        employeeRepository.save(SUPER_VALID_EMPLOYEE);

        MvcResult mvcResult = mockMvc.perform(get("/employees/{id}", 1L)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();
        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertThat(actualResponse).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(SUPER_VALID_EMPLOYEE_DTO));
    }

    @Test
    void gettingByIdWithInvalidID_ReturnNotFound() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/employees/{id}", 3L)
                        .contentType("application.json"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EmployeeNotFoundException))
                .andExpect(result -> assertEquals("Employee with id " + 3 + " not found", Objects.requireNonNull(result.getResolvedException()).getMessage()));

    }

    @Test
    void gettingAllFromAnEmptyTable_shouldReturnNoContent() throws Exception {

        mockMvc.perform(get("/employees"))
                .andExpect(status().isNoContent());
    }

    @Test
    void addNewEmployerWithValidBody_shouldReturnNewEmployee() throws Exception {
        departmentRepository.save(VALID_DEPARTMENT);
        MvcResult mvcResult = mockMvc.perform(post("/employees")
                        .content(objectMapper.writeValueAsString(SUPER_VALID_EMPLOYEE_DTO))
                        .contentType("application/json"))
                .andExpect(status().isCreated())
                .andReturn();
        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertThat(employeeService.getById(1L)).isEqualTo(SUPER_VALID_EMPLOYEE_DTO);
        assertThat(actualResponse).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(SUPER_VALID_EMPLOYEE_DTO));
    }

    @Test
    void whenAddingNewInvalidEmployee_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/employees")
                        .content(objectMapper.writeValueAsString(INVALID_EMPLOYEE_DTO))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void whenEditingAnEmployeeWithValidData_shouldReturnNewEmployeeData() throws Exception {
        departmentRepository.save(VALID_DEPARTMENT);
        employeeRepository.save(VALID_EMPLOYEE);
        MvcResult mvcResult = mockMvc.perform(put("/employees/{id}", 1L)
                        .content(objectMapper.writeValueAsString(NEW_EMPLOYEE_DTO))
                        .contentType("application/json"))
                .andExpect(status().isCreated())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertThat(employeeService.getById(1L)).isEqualTo(NEW_EMPLOYEE_DTO);
        assertThat(actualResponse).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(NEW_EMPLOYEE_DTO));
    }

    @Test
    void whenEditingAnEmployeeWithInvalidData_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(put("/employees/{id}", 1L)
                        .content(objectMapper.writeValueAsString(INVALID_EMPLOYEE_DTO))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}
