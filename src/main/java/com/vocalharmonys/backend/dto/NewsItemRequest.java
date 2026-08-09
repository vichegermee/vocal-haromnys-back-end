package com.vocalharmonys.backend.dto;

import jakarta.validation.constraints.NotBlank;

public record NewsItemRequest(
        @NotBlank String itemDate,
        @NotBlank String title
) {
}
