package com.vocalharmonys.backend.dto;

import com.vocalharmonys.backend.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * No username or password here on purpose — both are generated server-side
 * (see MemberService.create()), never supplied by the admin filling the form.
 */
public record CreateMemberRequest(
        @NotBlank(message = "Le prénom est requis.") String firstName,
        String lastName,
        @NotBlank @Email(message = "Adresse email invalide.") String email,
        Role role
) {
}
