package com.vocalharmonys.backend.dto;

import com.vocalharmonys.backend.entity.VoicePart;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record JoinApplicationRequest(
        @NotNull VoicePart voicePart,
        String experience,
        String availability,
        String audioLink,
        @NotBlank String motivation,
        @NotBlank String applicantName,
        @NotBlank @Email String applicantEmail
) {
}
