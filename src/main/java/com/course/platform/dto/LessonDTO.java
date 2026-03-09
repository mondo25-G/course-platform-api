package com.course.platform.dto;

public record LessonDTO(
        Long id,
        String title,
        String videoUrl,
        int position
) {}
