package com.vocalharmonys.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record ReservationRequest(
        @NotBlank String eventType,
        @NotNull LocalDate desiredDate,
        String budget,
        @NotBlank String location,
        String choristerCount,
        String message,
        @NotBlank String requesterName,
        @NotBlank @Email String requesterEmail
) {
}
