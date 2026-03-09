package com.course.platform.dto;

import java.util.List;

public record StudentDTO(
        Long id,
        String name,
        String email,
        List<CourseDTO> enrolledCourses
) {}