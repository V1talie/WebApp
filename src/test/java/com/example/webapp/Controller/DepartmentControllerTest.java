package com.example.webapp.Controller;

import com.example.webapp.Exception.DepartmentNotFoundException;
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

import static com.example.webapp.util.DEPARTMENT_LIST_DTO;
import static com.example.webapp.util.INVALID_DEPARTMENT_DTO;
import static com.example.webapp.util.NEW_DEPARTMENT_DTO;
import static com.example.webapp.util.VALID_DEPARTMENT_DTO;
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
class DepartmentControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DepartmentService departmentService;
    @MockBean
    private EmployeeService employeeService;


    @Test
    void whenGettingByIdWithInvalidID_ReturnNotFound() throws Exception {
        when(departmentService.getById(3L)).thenThrow(new DepartmentNotFoundException("Department with id " + 3 + " not found"));
        mockMvc.perform(MockMvcRequestBuilders.get("/departments/{id}", 3L)
                        .contentType("application.json"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof DepartmentNotFoundException))
                .andExpect(result -> assertEquals("Department with id " + 3 + " not found", Objects.requireNonNull(result.getResolvedException()).getMessage()));

        verify(departmentService).getById(3L);
    }

    @Test
    void whenGettingByIdWithValidID_shouldReturnDepartment() throws Exception {

        when(departmentService.getById(1L)).thenReturn(VALID_DEPARTMENT_DTO);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/departments/{id}", 1)
                        .contentType("application/json"))
                .andExpect(status().is(200))
                .andReturn();
        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertThat(actualResponse).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(VALID_DEPARTMENT_DTO));
        verify(departmentService).getById(1L);
    }

    @Test
    void whenGettingAllFromAnEmptyTable_shouldReturnNoContent() throws Exception {

        when(departmentService.getAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(MockMvcRequestBuilders.get("/departments"))
                .andExpect(status().is(204));
        verify(departmentService).getAll();
    }

    @Test
    void whenGettingAllFromTable_ReturnListOfDepartments() throws Exception {

        when(departmentService.getAll()).thenReturn(DEPARTMENT_LIST_DTO);
        MvcResult mvcResult = mockMvc.perform(get("/departments")
                        .contentType("application/json"))
                .andExpect(status().is(200))
                .andReturn();
        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertThat(actualResponse).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(DEPARTMENT_LIST_DTO));
        verify(departmentService).getAll();
    }

    @Test
    void whenAddingNewValidDepartment_shouldReturnDepartment() throws Exception {
        when(departmentService.add(VALID_DEPARTMENT_DTO)).thenReturn(VALID_DEPARTMENT_DTO);

        MvcResult mvcResult = mockMvc.perform(post("/departments")
                        .content(objectMapper.writeValueAsString(VALID_DEPARTMENT_DTO))
                        .contentType("application/json"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertThat(actualResponse).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(VALID_DEPARTMENT_DTO));
        verify(departmentService).add(VALID_DEPARTMENT_DTO);
    }

    @Test
    void whenAddingNewInvalidDepartment_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/departments")
                        .content(objectMapper.writeValueAsString(INVALID_DEPARTMENT_DTO))
                        .contentType("application/json"))
                .andExpect(status().is(400))
                .andReturn();
    }

    @Test
    void whenEditingAnDepartmentWithValidData_shouldReturnNewDepartmentData() throws Exception {
        when(departmentService.updateDepartment(1L, NEW_DEPARTMENT_DTO)).thenReturn(NEW_DEPARTMENT_DTO);

        MvcResult mvcResult = mockMvc.perform(put("/departments/{id}", 1L)
                        .content(objectMapper.writeValueAsString(NEW_DEPARTMENT_DTO))
                        .contentType("application/json"))
                .andExpect(status().isCreated())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertThat(actualResponse).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(NEW_DEPARTMENT_DTO));
        verify(departmentService).updateDepartment(1, NEW_DEPARTMENT_DTO);
    }

    @Test
    void whenEditingADepartmentWithInvalidData_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(put("/departments/{id}", 1L)
                        .content(objectMapper.writeValueAsString(INVALID_DEPARTMENT_DTO))
                        .contentType("application/json"))
                .andExpect(status().is(400))
                .andReturn();

    }
}
