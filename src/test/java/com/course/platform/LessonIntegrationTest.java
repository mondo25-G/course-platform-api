package com.course.platform;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
public class LessonIntegrationTest extends BaseTest{

    @Test
    void shouldGetLessons() throws Exception {
        mockMvc.perform(get("/api/lessons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void shouldGetLesson() throws Exception {
        mockMvc.perform(get("/api/lessons/1")).andExpect(status().isOk());
    }

    @Test
    void shouldCreateLesson() throws Exception {
        Map<String, Object> request = Map.of(
                "title", "lecture3",
                "videoUrl", "videoUrl3",
                "position", 3
        );
        mockMvc.perform(post("/api/lessons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .param("courseId","2"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.title").value("lecture3"))
                .andExpect(jsonPath("$.videoUrl").value("videoUrl3"))
                .andExpect(jsonPath("$.position").value(3));
    }

    @Test
    void shouldUpdateLesson() throws Exception {
        Map<String, Object> request = Map.of(
                "title", "lecture1",
                "videoUrl", "videoUrl1",
                "position", 1
        );

        mockMvc.perform(put("/api/lessons/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.title").value("lecture1"))
                .andExpect(jsonPath("$.videoUrl").value("videoUrl1"))
                .andExpect(jsonPath("$.position").value(1));
    }


    @Test
    void shouldDeleteLesson() throws Exception {
        mockMvc.perform(delete("/api/lessons/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldFailValidationWhenTitleIsMissing() throws Exception {
        Map<String, Object> request = Map.of(
                "videoUrl", "videoUrl1",
                "position", 1
        );
        mockMvc.perform(post("/api/lessons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                        .param("courseId","1"))
                .andExpect(status().isBadRequest());

    }

    @Test
    void shouldFailValidationWhenCourseIsMissing() throws Exception {
        Map<String, Object> request = Map.of(
                "title", "title1",
                "videoUrl", "videoUrl1",
                "position", 1
        );
        mockMvc.perform(post("/api/lessons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

    }

}
