package com.vocalharmonys.backend.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "L'identifiant est requis.") String username,
        @NotBlank(message = "Le mot de passe est requis.") String password
) {
}
