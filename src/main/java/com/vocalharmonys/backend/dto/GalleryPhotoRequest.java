package com.vocalharmonys.backend.dto;

import jakarta.validation.constraints.NotBlank;

public record GalleryPhotoRequest(
        @NotBlank String label,
        @NotBlank String imageUrl,
        int displayOrder
) {
}
