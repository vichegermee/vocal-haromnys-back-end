package com.vocalharmonys.backend.entity;

/**
 * Lifecycle of anything a visitor submits and the chorale later has to act on:
 * donations, "réserver une prestation" requests, "rejoindre la chorale"
 * applications, and CD orders all share this.
 */
public enum RequestStatus {
    RECEIVED,
    IN_PROGRESS,
    DONE
}
