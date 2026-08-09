package com.vocalharmonys.backend.service;

import com.vocalharmonys.backend.dto.EventRequest;
import com.vocalharmonys.backend.dto.EventResponse;
import com.vocalharmonys.backend.entity.Event;
import com.vocalharmonys.backend.exception.ResourceNotFoundException;
import com.vocalharmonys.backend.repository.EventRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    /** @param type "upcoming", "past", or anything else (including null) for every event. */
    public List<EventResponse> list(String type) {
        LocalDate today = LocalDate.now();
        List<Event> events = switch (type == null ? "" : type) {
            case "upcoming" -> eventRepository.findByEventDateGreaterThanEqualOrderByEventDateAsc(today);
            case "past" -> eventRepository.findByEventDateLessThanOrderByEventDateDesc(today);
            default -> eventRepository.findAllByOrderByEventDateAsc();
        };
        return events.stream().map(EventResponse::from).toList();
    }

    public EventResponse create(EventRequest request) {
        Event event = new Event();
        applyRequest(event, request);
        return EventResponse.from(eventRepository.save(event));
    }

    public EventResponse update(Long id, EventRequest request) {
        Event event = findOrThrow(id);
        applyRequest(event, request);
        return EventResponse.from(eventRepository.save(event));
    }

    public void delete(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new ResourceNotFoundException("Événement introuvable : " + id);
        }
        eventRepository.deleteById(id);
    }

    private Event findOrThrow(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Événement introuvable : " + id));
    }

    private void applyRequest(Event event, EventRequest request) {
        event.setTitle(request.title());
        event.setEventDate(request.eventDate());
        event.setLocation(request.location());
        event.setDescription(request.description());
    }
}
