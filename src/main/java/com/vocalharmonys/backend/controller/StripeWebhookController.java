package com.vocalharmonys.backend.controller;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import com.vocalharmonys.backend.service.StripeWebhookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Receives Stripe's server-to-server webhook calls. Intentionally exempt
 * from JWT auth (see SecurityConfig) — Stripe authenticates itself via the
 * {@code Stripe-Signature} header, verified below, not a bearer token.
 *
 * <p>The {@code payload} parameter is typed {@code String}, not a POJO/Map:
 * Spring routes a String-typed {@code @RequestBody} through {@code
 * StringHttpMessageConverter}, which hands back the exact raw request bytes
 * untouched by Jackson. {@link Webhook#constructEvent} needs that exact raw
 * payload to recompute the HMAC signature and compare it against the header
 * — re-serializing a parsed object would almost certainly not match
 * byte-for-byte, so no custom filter/body-wrapper is needed here.
 */
@RestController
@RequestMapping("/api/webhooks")
public class StripeWebhookController {

    private static final Logger log = LoggerFactory.getLogger(StripeWebhookController.class);

    private final StripeWebhookService stripeWebhookService;
    private final String webhookSecret;

    public StripeWebhookController(
            StripeWebhookService stripeWebhookService,
            @Value("${app.stripe.webhook-secret}") String webhookSecret
    ) {
        this.stripeWebhookService = stripeWebhookService;
        this.webhookSecret = webhookSecret;
    }

    @PostMapping("/stripe")
    public ResponseEntity<Void> handleStripeWebhook(
            @RequestBody String payload,
            @RequestHeader(value = "Stripe-Signature", required = false) String signatureHeader
    ) {
        // required = false + a manual null check (rather than the default
        // required = true) so a call missing the header — malformed/hostile,
        // since Stripe itself always sends it — gets the same clean 400 as
        // an invalid signature, instead of Spring's binding exception
        // falling through to GlobalExceptionHandler's generic 500.
        if (signatureHeader == null) {
            log.warn("Rejected a Stripe webhook call with no Stripe-Signature header.");
            return ResponseEntity.badRequest().build();
        }

        Event event;
        try {
            event = Webhook.constructEvent(payload, signatureHeader, webhookSecret);
        } catch (SignatureVerificationException e) {
            log.warn("Rejected a Stripe webhook call with an invalid signature.");
            return ResponseEntity.badRequest().build();
        }

        stripeWebhookService.handle(event);
        return ResponseEntity.ok().build();
    }
}
