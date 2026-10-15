package com.vocalharmonys.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

/**
 * A performance, past or future. There's no "upcoming"/"past" flag to keep in
 * sync — {@code EventService} computes that by comparing {@link #eventDate}
 * to today, so nothing ever goes stale.
 */
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(name = "event_date", nullable = false)
    private LocalDate eventDate;

    @Column(nullable = false, length = 200)
    private String location;

    @Column(columnDefinition = "text")
    private String description;

    // Longer write-up shown in the "En détail" section — description stays
    // the short teaser shown on the "À venir" cards.
    @Column(name = "detailed_description", columnDefinition = "text")
    private String detailedDescription;

    // External ticketing page (e.g. HelloAsso). Null when the event has no online booking.
    @Column(name = "booking_url", length = 500)
    private String bookingUrl;

    // Poster shown in the home page's "À la une" spotlight. Any upcoming event
    // with a poster is eligible; the home page picks the soonest one.
    @Column(name = "poster_url", length = 500)
    private String posterUrl;

    public Event() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetailedDescription() {
        return detailedDescription;
    }

    public void setDetailedDescription(String detailedDescription) {
        this.detailedDescription = detailedDescription;
    }

    public String getBookingUrl() {
        return bookingUrl;
    }

    public void setBookingUrl(String bookingUrl) {
        this.bookingUrl = bookingUrl;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }
}
