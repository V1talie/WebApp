package com.example.webapp.Controller;

import com.example.webapp.Exception.EmployeeNotFoundException;
import com.example.webapp.Service.DepartmentService;
import com.example.webapp.Service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.Objects;

import static com.example.webapp.util.EMPLOYEE_LIST_DTO;
import static com.example.webapp.util.INVALID_EMPLOYEE_DTO;
import static com.example.webapp.util.NEW_EMPLOYEE_DTO;
import static com.example.webapp.util.VALID_EMPLOYEE_DTO;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
@AutoConfigureMockMvc
class EmployeeControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private EmployeeService employeeService;
    @MockBean
    private DepartmentService departmentService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenGettingByIdWithInvalidID_ReturnNotFound() throws Exception {
        when(employeeService.getById(3L)).thenThrow(new EmployeeNotFoundException("Employee with id " + 3 + " not found"));
        mockMvc.perform(MockMvcRequestBuilders.get("/employees/{id}", 3L)
                        .contentType("application.json"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EmployeeNotFoundException))
                .andExpect(result -> assertEquals("Employee with id " + 3 + " not found", Objects.requireNonNull(result.getResolvedException()).getMessage()));


        verify(employeeService).getById(3L);
    }

    @Test
    void whenGettingByIdWithValidID_shouldReturnEmployee() throws Exception {

        when(employeeService.getById(3L)).thenReturn(VALID_EMPLOYEE_DTO);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/employees/{id}", 3)
                        .contentType("application/json"))
                .andExpect(status().is(200))
                .andReturn();
        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertThat(actualResponse).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(VALID_EMPLOYEE_DTO));
        verify(employeeService).getById(3L);
    }

    @Test
    void whenGettingAllFromAnEmptyTable_shouldReturnNoContent() throws Exception {

        when(employeeService.getAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/employees"))
                .andExpect(status().is(204));

        verify(employeeService).getAll();
    }

    @Test
    void whenGettingAllFromTable_ReturnListOfEmployees() throws Exception {

        when(employeeService.getAll()).thenReturn(EMPLOYEE_LIST_DTO);
        MvcResult mvcResult = mockMvc.perform(get("/employees")
                        .contentType("application/json"))
                .andExpect(status().is(200))
                .andReturn();
        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertThat(actualResponse).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(EMPLOYEE_LIST_DTO));
        verify(employeeService).getAll();
    }

    @Test
    void whenAddingNewValidEmployee_shouldReturnEmployee() throws Exception {
        when(employeeService.addEmployee(VALID_EMPLOYEE_DTO)).thenReturn(VALID_EMPLOYEE_DTO);

        MvcResult mvcResult = mockMvc.perform(post("/employees")
                        .content(objectMapper.writeValueAsString(VALID_EMPLOYEE_DTO))
                        .contentType("application/json"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertThat(actualResponse).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(VALID_EMPLOYEE_DTO));
        verify(employeeService).addEmployee(VALID_EMPLOYEE_DTO);
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
        when(employeeService.updateEmployee(1L, NEW_EMPLOYEE_DTO)).thenReturn(NEW_EMPLOYEE_DTO);

        MvcResult mvcResult = mockMvc.perform(put("/employees/{id}", 1L)
                        .content(objectMapper.writeValueAsString(NEW_EMPLOYEE_DTO))
                        .contentType("application/json"))
                .andExpect(status().isCreated())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertThat(actualResponse).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(NEW_EMPLOYEE_DTO));
        verify(employeeService).updateEmployee(1, NEW_EMPLOYEE_DTO);
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
