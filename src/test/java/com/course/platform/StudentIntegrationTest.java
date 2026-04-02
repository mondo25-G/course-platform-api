package com.course.platform;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StudentIntegrationTest extends BaseTest {

    @Test
    void shouldCreateInstructor() throws Exception{

        Map<String,String> request = Map.of(
                "name","Mike Benson",
                "email", "mike.benson@gmail.com"
        );

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Mike Benson"))
                .andExpect(jsonPath("$.email").value("mike.benson@gmail.com"))
                .andExpect(jsonPath("$.id").value(3));
    }

    @Test
    void shouldGetAllIstructors() throws Exception {
        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void shouldGetIstructorById() throws Exception {
        mockMvc.perform(get("/api/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldUpdateInstructor() throws Exception {
        Map<String,String> request = Map.of(
                "name","Mike Benson",
                "email", "mike.benson@gmail.com"
        );

        mockMvc.perform(put("/api/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Mike Benson"))
                .andExpect(jsonPath("$.email").value("mike.benson@gmail.com"));
    }

    @Test
    void shouldDeleteInstructor() throws Exception {
        mockMvc.perform(delete("/api/instructors/1")).andExpect(status().isNoContent());
    }

    @Test
    void shouldFailWhenInsertDuplicateMail() throws Exception {
        Map<String,String> request = Map.of(
                "name","Jack Brown",
                "email", "alice@email.com"
        );

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Test
    void shouldFailValidationWhenEmailMissing() throws Exception {
        Map<String, Object> request = Map.of(
                "name", "No email provided"
        );

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
