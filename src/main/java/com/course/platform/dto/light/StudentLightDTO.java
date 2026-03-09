package com.course.platform.dto.light;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record StudentLightDTO(
        @NotBlank(message = "Name cannot be blank")
        String name,
        @Email(message = "Invalid email format")
        @NotBlank(message = "Email cannot be blank")
        String email
) {}
