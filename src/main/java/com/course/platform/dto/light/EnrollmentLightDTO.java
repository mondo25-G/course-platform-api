package com.course.platform.dto.light;

import jakarta.validation.constraints.NotNull;

public record EnrollmentLightDTO(
        @NotNull(message = "Student ID is required")
        Long studentId,
        @NotNull(message = "Course ID is required")
        Long courseId
) {}