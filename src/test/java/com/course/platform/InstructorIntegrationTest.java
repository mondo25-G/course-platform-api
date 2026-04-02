package com.course.platform;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class InstructorIntegrationTest extends BaseTest {

    @Test
    void shouldCreateInstructor() throws Exception{

        Map<String,String> request = Map.of(
                "name","Jack Brown",
                "email", "j.brown@gmail.com"
        );

        mockMvc.perform(post("/api/instructors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Jack Brown"))
                .andExpect(jsonPath("$.email").value("j.brown@gmail.com"))
                .andExpect(jsonPath("$.id").value(3));
    }

    @Test
    void shouldGetAllIstructors() throws Exception {
        mockMvc.perform(get("/api/instructors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void shouldGetIstructorById() throws Exception {
        mockMvc.perform(get("/api/instructors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldUpdateInstructor() throws Exception {
        Map<String,String> request = Map.of(
                "name","Jack Brown",
                "email", "j.brown@gmail.com"
        );

        mockMvc.perform(put("/api/instructors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Jack Brown"))
                .andExpect(jsonPath("$.email").value("j.brown@gmail.com"));
    }

    @Test
    void shouldDeleteInstructor() throws Exception {
        mockMvc.perform(delete("/api/instructors/1")).andExpect(status().isNoContent());
    }

    @Test
    void shouldFailWhenInsertDuplicateMail() throws Exception {
        Map<String,String> request = Map.of(
                "name","Jack Brown",
                "email", "john.doe@email.com"
        );

        mockMvc.perform(post("/api/instructors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Test
    void shouldFailValidationWhenEmailMissing() throws Exception {
        Map<String, Object> request = Map.of(
                "name", "No email provided"
        );

        mockMvc.perform(post("/api/instructors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

}
