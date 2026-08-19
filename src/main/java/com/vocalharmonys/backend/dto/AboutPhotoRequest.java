package com.vocalharmonys.backend.dto;

import jakarta.validation.constraints.NotBlank;

public record AboutPhotoRequest(
        @NotBlank String imageUrl,
        int displayOrder
) {
}
