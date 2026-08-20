package com.vocalharmonys.backend.dto;

import com.vocalharmonys.backend.entity.Event;
import java.time.LocalDate;

public record EventResponse(
        Long id,
        String title,
        LocalDate eventDate,
        String location,
        String description,
        String detailedDescription,
        boolean past
) {

    public static EventResponse from(Event event) {
        return new EventResponse(
                event.getId(),
                event.getTitle(),
                event.getEventDate(),
                event.getLocation(),
                event.getDescription(),
                event.getDetailedDescription(),
                event.getEventDate().isBefore(LocalDate.now())
        );
    }
}
