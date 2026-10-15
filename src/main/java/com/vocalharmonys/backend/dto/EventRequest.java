package com.vocalharmonys.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record EventRequest(
        @NotBlank String title,
        @NotNull LocalDate eventDate,
        @NotBlank String location,
        String description,
        String detailedDescription,
        @Size(max = 500) String bookingUrl,
        @Size(max = 500) String posterUrl
) {
}
