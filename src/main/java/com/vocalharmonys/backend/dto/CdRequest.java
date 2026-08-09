package com.vocalharmonys.backend.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CdRequest(
        @NotBlank String title,
        int releaseYear,
        @NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal price,
        @NotBlank String description,
        @NotBlank String imageUrl
) {
}
