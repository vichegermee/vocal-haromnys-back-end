package com.vocalharmonys.backend.dto;

import com.vocalharmonys.backend.entity.Reservation;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ReservationResponse(
        Long id,
        String eventType,
        LocalDate desiredDate,
        String budget,
        String location,
        String choristerCount,
        String message,
        String requesterName,
        String requesterEmail,
        String status,
        LocalDateTime createdAt
) {

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getEventType(),
                reservation.getDesiredDate(),
                reservation.getBudget(),
                reservation.getLocation(),
                reservation.getChoristerCount(),
                reservation.getMessage(),
                reservation.getRequesterName(),
                reservation.getRequesterEmail(),
                reservation.getStatus().name(),
                reservation.getCreatedAt()
        );
    }
}
