package com.example.webapp.Integration;

import com.example.webapp.Exception.DepartmentNotFoundException;
import com.example.webapp.Repository.DepartmentRepository;
import com.example.webapp.Service.DepartmentService;
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

import static com.example.webapp.util.DEPARTMENT_LIST;
import static com.example.webapp.util.INVALID_DEPARTMENT_DTO;
import static com.example.webapp.util.NEW_DEPARTMENT_DTO;
import static com.example.webapp.util.REVERSE_DEPARTMENT_LIST_DTO;
import static com.example.webapp.util.VALID_DEPARTMENT;
import static com.example.webapp.util.VALID_DEPARTMENT_DTO;
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
class DepartmentControllerIntegrationTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    Flyway flyway;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DepartmentService departmentService;

    @BeforeEach
    public void cleanUp() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    void getAll_shouldReturnAllDepartments() throws Exception {
        departmentRepository.saveAll(DEPARTMENT_LIST);
        MvcResult mvcResult = mockMvc.perform(get("/departments")
                        .contentType("application/json"))
                .andExpect(status().is(200))
                .andReturn();
        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertThat(actualResponse).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(REVERSE_DEPARTMENT_LIST_DTO));
    }

    @Test
    void getDepartmentById_shouldReturnTheDepartment() throws Exception {
        departmentRepository.save(VALID_DEPARTMENT);

        MvcResult mvcResult = mockMvc.perform(get("/departments/{id}", 1L)
                        .contentType("application/json"))
                .andExpect(status().is(200))
                .andReturn();
        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertThat(actualResponse).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(VALID_DEPARTMENT_DTO));
    }

    @Test
    void gettingByIdWithInvalidID_ReturnNotFound() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/departments/{id}", 99L)
                        .contentType("application.json"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof DepartmentNotFoundException))
                .andExpect(result -> assertEquals("Department with id " + 99 + " not found", Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    void gettingAllFromAnEmptyTable_shouldReturnNoContent() throws Exception {
        // departmentRepository.deleteAll();
        mockMvc.perform(get("/departments"))
                .andExpect(status().isNoContent());
    }

    @Test
    void addNewDepartmentWithValidBody_shouldReturnNewDepartment() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/departments")
                        .content(objectMapper.writeValueAsString(VALID_DEPARTMENT_DTO))
                        .contentType("application/json"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertThat(departmentService.getById(1L)).isEqualTo(VALID_DEPARTMENT_DTO);
        assertThat(actualResponse).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(VALID_DEPARTMENT_DTO));
    }

    @Test
    void whenAddingNewInvalidDepartment_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/departments")
                        .content(objectMapper.writeValueAsString(INVALID_DEPARTMENT_DTO))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void whenEditingAnDepartmentWithValidData_shouldReturnNewDepartmentData() throws Exception {
        departmentRepository.save(VALID_DEPARTMENT);
        MvcResult mvcResult = mockMvc.perform(put("/departments/{id}", 1)
                        .content(objectMapper.writeValueAsString(NEW_DEPARTMENT_DTO))
                        .contentType("application/json"))
                .andExpect(status().isCreated())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertThat(departmentService.getById(1L)).isEqualTo(NEW_DEPARTMENT_DTO);
        assertThat(actualResponse).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(NEW_DEPARTMENT_DTO));
    }

    @Test
    void whenEditingAnDepartmentWithInvalidData_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(put("/departments/{id}", 1L)
                        .content(objectMapper.writeValueAsString(INVALID_DEPARTMENT_DTO))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}
