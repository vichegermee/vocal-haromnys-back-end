package com.vocalharmonys.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record EventRequest(
        @NotBlank String title,
        @NotNull LocalDate eventDate,
        @NotBlank String location,
        String description
) {
}
