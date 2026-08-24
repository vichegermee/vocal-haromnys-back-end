package com.vocalharmonys.backend.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record BulkImportMemberRequest(
        @NotEmpty(message = "La liste est vide.") @Valid List<Row> members
) {
    public record Row(
            @NotBlank(message = "Le prénom est requis.") String firstName,
            String lastName,
            @NotBlank @Email(message = "Adresse email invalide.") String email
    ) {
    }
}
