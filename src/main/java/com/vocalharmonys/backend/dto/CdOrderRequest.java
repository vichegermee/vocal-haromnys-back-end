package com.vocalharmonys.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CdOrderRequest(
        @NotNull Long cdId,
        @NotBlank String customerName,
        @NotBlank @Email String customerEmail,
        @Min(1) int quantity,
        String message
) {
}
