package com.course.platform.dto;

import java.time.LocalDateTime;

public record EnrollmentDTO(
        Long id,
        Long studentId,
        Long courseId,
        String courseTitle,
        String studentName,
        LocalDateTime enrolledAt
) {}