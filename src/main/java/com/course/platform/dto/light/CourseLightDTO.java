package com.course.platform.dto.light;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CourseLightDTO(
        @NotBlank(message = "Title cannot be blank")
        @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
        String title,
        @NotBlank(message = "Description cannot be blank")
        @Size(min = 10, max = 500, message = "Description must be between 10 and 500 characters")
        String description) {

}
