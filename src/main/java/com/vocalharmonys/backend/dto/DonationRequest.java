package com.vocalharmonys.backend.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record DonationRequest(
        // Stripe's minimum charge amount in EUR is €0.50 — a lower amount
        // would otherwise fail at Stripe's side with a less friendly error.
        @NotNull @DecimalMin("0.50") BigDecimal amount,
        @NotBlank String donorName,
        @NotBlank @Email String donorEmail
) {
}
