package com.vocalharmonys.backend.dto;

import jakarta.validation.constraints.NotBlank;

public record PartnerRequest(
        @NotBlank String label,
        @NotBlank String imageUrl,
        int displayOrder
) {
}
