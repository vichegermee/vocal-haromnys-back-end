package com.vocalharmonys.backend.dto;

/** Returned right after a Checkout Session is created — the frontend redirects the browser to {@code checkoutUrl}. */
public record CdOrderCheckoutResponse(Long cdOrderId, String checkoutUrl) {
}
