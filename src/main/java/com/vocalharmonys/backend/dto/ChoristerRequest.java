package com.vocalharmonys.backend.dto;

import com.vocalharmonys.backend.entity.VoicePart;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ChoristerRequest(
        @NotBlank String name,
        @NotNull VoicePart voicePart,
        @NotBlank String description,
        @NotBlank String imageUrl,
        int displayOrder
) {
}
