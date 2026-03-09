package com.course.platform.dto.light;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record LessonLightDTO(
        @NotBlank(message = "Title cannot be blank")
        String title,
        @NotBlank(message = "Video URL cannot be blank")
        String videoUrl,
        @NotNull(message = "Position is required")
        @Positive(message = "Position must be positive")
        Integer position
) {}