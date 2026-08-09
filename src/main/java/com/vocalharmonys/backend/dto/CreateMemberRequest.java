package com.vocalharmonys.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateMemberRequest(
        @NotBlank(message = "L'identifiant est requis.") String username,
        @NotBlank @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères.") String password,
        @NotBlank(message = "Le nom complet est requis.") String fullName
) {
}
