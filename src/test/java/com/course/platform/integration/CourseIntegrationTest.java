package com.course.platform.integration;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.Map;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CourseIntegrationTest extends BaseTest {



    @Test
    void shouldCreateCourse() throws Exception {
        Map<String,Object> request = Map.of(
                "title", "Spring Boot Mastery",
                "description", "Advanced course on Spring Boot"
        );
        //CourseLightDTO request = new CourseLightDTO("Spring Boot Mastery","Advanced course on Spring Boot");
        mockMvc.perform(post("/api/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .param("instructorId","1"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Spring Boot Mastery"))
                .andExpect(jsonPath("$.description").value("Advanced course on Spring Boot"));
    }

    // --- GET ALL COURSES ---
    @Test
    void shouldGetAllCourses() throws Exception {
        mockMvc.perform(get("/api/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    // --- GET COURSE BY ID ---
    @Test
    void shouldGetCourseById() throws Exception {
        mockMvc.perform(get("/api/courses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    // --- UPDATE COURSE ---
    @Test
    void shouldUpdateCourse() throws Exception {
        Map<String, Object> request = Map.of(
                "title", "Updated Course Title",
                "description", "Updated Description"
        );

        mockMvc.perform(put("/api/courses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Course Title"))
                .andExpect(jsonPath("$.description").value("Updated Description"));
    }

    // --- DELETE COURSE ---
    @Test
    void shouldDeleteCourse() throws Exception {
        mockMvc.perform(delete("/api/courses/1"))
                .andExpect(status().isNoContent());
    }

    // --- VALIDATION FAILURE ---
    @Test
    void shouldFailValidationWhenTitleMissing() throws Exception {
        Map<String, Object> request = Map.of(
                "description", "No title provided"
        );

        mockMvc.perform(post("/api/courses")
                        .param("instructorId","1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldFailValidationWhenInstructorMissing() throws Exception {
        Map<String, Object> request = Map.of(
                    "title","title",
                "description", "description"
        );

        mockMvc.perform(post("/api/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
