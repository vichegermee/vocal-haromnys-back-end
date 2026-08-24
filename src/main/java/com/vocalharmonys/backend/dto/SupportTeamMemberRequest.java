package com.vocalharmonys.backend.dto;

import jakarta.validation.constraints.NotBlank;

public record SupportTeamMemberRequest(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank String title,
        String photoFilename,
        int displayOrder
) {
}
