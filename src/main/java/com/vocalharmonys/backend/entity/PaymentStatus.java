package com.vocalharmonys.backend.entity;

/** Where a Stripe Checkout session actually stands for a donation or a CD order, distinct from {@link RequestStatus}. */
public enum PaymentStatus {
    PENDING,
    PAID,
    FAILED,
    EXPIRED
}
