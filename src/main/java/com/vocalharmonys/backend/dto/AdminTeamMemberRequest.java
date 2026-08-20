package com.vocalharmonys.backend.dto;

import jakarta.validation.constraints.NotBlank;

public record AdminTeamMemberRequest(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank String title,
        @NotBlank String photoFilename,
        int displayOrder
) {
}
