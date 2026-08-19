package com.vocalharmonys.backend.dto;

import jakarta.validation.constraints.NotBlank;

public record HomeBannerRequest(
        @NotBlank String imageUrl,
        int displayOrder
) {
}
