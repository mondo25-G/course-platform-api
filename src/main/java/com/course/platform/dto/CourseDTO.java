package com.course.platform.dto;

import java.util.List;

public record CourseDTO(
        Long id,
        String title,
        String description,
        Long instructorId,
        List<LessonDTO> lessons,
        long enrollmentCount
) {}