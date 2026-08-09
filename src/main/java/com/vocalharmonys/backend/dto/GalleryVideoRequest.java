package com.vocalharmonys.backend.dto;

import jakarta.validation.constraints.NotBlank;

public record GalleryVideoRequest(
        @NotBlank String title,
        @NotBlank String youtubeId,
        int displayOrder
) {
}
