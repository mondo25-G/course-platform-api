package com.course.platform.dto;

import java.util.List;

public record InstructorDTO(
        Long id,
        String name,
        String email,
        List<CourseDTO> courses
) {}