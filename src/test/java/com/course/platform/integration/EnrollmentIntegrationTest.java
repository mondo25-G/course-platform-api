package com.course.platform.integration;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EnrollmentIntegrationTest extends BaseTest {
    @Test
    void shouldGetEnrollments() throws Exception {
        mockMvc.perform(get("/api/enrollments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void shouldGetEnrollment() throws Exception {
        mockMvc.perform(get("/api/enrollments/1")).andExpect(status().isOk());
    }

    @Test
    void shouldCreateEnrollment() throws Exception {
        Map<String, Object> request = Map.of(
                "studentId", 1,
                "courseId", 2
        );
        mockMvc.perform(post("/api/enrollments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.studentId").value(1))
                .andExpect(jsonPath("$.courseId").value(2))
                .andExpect(jsonPath("$.studentName").value("Alice Brown"))
                .andExpect(jsonPath("$.courseTitle").value("Java Fundamentals"));
    }

    @Test
    void shouldDeleteEnrollment() throws Exception {
        mockMvc.perform(delete("/api/enrollments/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldFailValidationWhenDuplicateEnrollment() throws Exception {
        Map<String, Object> request = Map.of(
                "studentId", 1,
                "courseId", 1
        );
        mockMvc.perform(post("/api/enrollments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }
}
