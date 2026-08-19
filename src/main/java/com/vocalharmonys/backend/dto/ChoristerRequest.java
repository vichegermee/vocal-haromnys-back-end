package com.vocalharmonys.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ChoristerRequest(
        @NotBlank String name,
        @NotNull String voicePart,
        @NotBlank String description,
        @NotBlank String imageUrl,
        int displayOrder
) {
}
