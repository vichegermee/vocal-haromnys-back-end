package com.vocalharmonys.backend.service;

import com.stripe.exception.EventDataObjectDeserializationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.checkout.Session;
import com.vocalharmonys.backend.email.EmailService;
import com.vocalharmonys.backend.entity.CdOrder;
import com.vocalharmonys.backend.entity.Donation;
import com.vocalharmonys.backend.entity.PaymentStatus;
import com.vocalharmonys.backend.repository.CdOrderRepository;
import com.vocalharmonys.backend.repository.DonationRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Reacts to Stripe Checkout events once {@link
 * com.vocalharmonys.backend.controller.StripeWebhookController} has verified
 * the request actually came from Stripe. Signature verification happens
 * there, not here — this class only trusts events it's handed. A single
 * webhook endpoint serves both donations and CD orders — the session's
 * {@code type} metadata (set in StripeCheckoutService) says which one.
 */
@Service
public class StripeWebhookService {

    private static final Logger log = LoggerFactory.getLogger(StripeWebhookService.class);
    private static final String TYPE_CD_ORDER = "cd_order";

    private final DonationRepository donationRepository;
    private final CdOrderRepository cdOrderRepository;
    private final EmailService emailService;

    public StripeWebhookService(
            DonationRepository donationRepository,
            CdOrderRepository cdOrderRepository,
            EmailService emailService
    ) {
        this.donationRepository = donationRepository;
        this.cdOrderRepository = cdOrderRepository;
        this.emailService = emailService;
    }

    public void handle(Event event) {
        switch (event.getType()) {
            case "checkout.session.completed", "checkout.session.async_payment_succeeded" -> handlePaid(event);
            case "checkout.session.expired" -> updateStatusIfNotPaid(event, PaymentStatus.EXPIRED);
            case "checkout.session.async_payment_failed" -> updateStatusIfNotPaid(event, PaymentStatus.FAILED);
            default -> log.debug("Ignoring unhandled Stripe event type: {}", event.getType());
        }
    }

    private void handlePaid(Event event) {
        Session session = deserializeSession(event);
        if (session == null || !"paid".equals(session.getPaymentStatus())) {
            return;
        }

        if (TYPE_CD_ORDER.equals(metadataType(session))) {
            handleCdOrderPaid(session);
        } else {
            handleDonationPaid(session);
        }
    }

    private void handleDonationPaid(Session session) {
        Optional<Donation> maybeDonation = findDonation(session);
        if (maybeDonation.isEmpty()) {
            log.error("Received a paid checkout.session event with no matching donation, session id={}", session.getId());
            return;
        }

        Donation donation = maybeDonation.get();
        if (donation.getPaymentStatus() == PaymentStatus.PAID) {
            // Stripe retries webhook delivery — this guards against sending
            // the recap email twice for the same payment.
            return;
        }

        donation.setPaymentStatus(PaymentStatus.PAID);
        donation.setPaidAt(LocalDateTime.now());
        donation.setStripePaymentIntentId(session.getPaymentIntent());
        donationRepository.save(donation);

        emailService.notifyDonationPaid(donation);
    }

    private void handleCdOrderPaid(Session session) {
        Optional<CdOrder> maybeOrder = findCdOrder(session);
        if (maybeOrder.isEmpty()) {
            log.error("Received a paid checkout.session event with no matching CD order, session id={}", session.getId());
            return;
        }

        CdOrder order = maybeOrder.get();
        if (order.getPaymentStatus() == PaymentStatus.PAID) {
            return;
        }

        order.setPaymentStatus(PaymentStatus.PAID);
        order.setPaidAt(LocalDateTime.now());
        order.setStripePaymentIntentId(session.getPaymentIntent());
        cdOrderRepository.save(order);

        emailService.notifyCdOrderPaid(order);
    }

    private void updateStatusIfNotPaid(Event event, PaymentStatus newStatus) {
        Session session = deserializeSession(event);
        if (session == null) {
            return;
        }

        if (TYPE_CD_ORDER.equals(metadataType(session))) {
            findCdOrder(session).ifPresent(order -> {
                if (order.getPaymentStatus() != PaymentStatus.PAID) {
                    order.setPaymentStatus(newStatus);
                    cdOrderRepository.save(order);
                }
            });
        } else {
            findDonation(session).ifPresent(donation -> {
                if (donation.getPaymentStatus() != PaymentStatus.PAID) {
                    donation.setPaymentStatus(newStatus);
                    donationRepository.save(donation);
                }
            });
        }
    }

    private String metadataType(Session session) {
        return session.getMetadata() != null ? session.getMetadata().get("type") : null;
    }

    private Optional<Donation> findDonation(Session session) {
        String donationId = session.getMetadata() != null ? session.getMetadata().get("donationId") : null;
        if (donationId != null) {
            try {
                return donationRepository.findById(Long.valueOf(donationId));
            } catch (NumberFormatException e) {
                log.warn("Checkout session {} had a non-numeric donationId metadata value: {}", session.getId(), donationId);
            }
        }
        return donationRepository.findByStripeCheckoutSessionId(session.getId());
    }

    private Optional<CdOrder> findCdOrder(Session session) {
        String cdOrderId = session.getMetadata() != null ? session.getMetadata().get("cdOrderId") : null;
        if (cdOrderId != null) {
            try {
                return cdOrderRepository.findById(Long.valueOf(cdOrderId));
            } catch (NumberFormatException e) {
                log.warn("Checkout session {} had a non-numeric cdOrderId metadata value: {}", session.getId(), cdOrderId);
            }
        }
        return cdOrderRepository.findByStripeCheckoutSessionId(session.getId());
    }

    private Session deserializeSession(Event event) {
        EventDataObjectDeserializer deserializer = event.getDataObjectDeserializer();
        Object stripeObject = deserializer.getObject().orElse(null);
        if (stripeObject == null) {
            // Can happen if the event was serialized with a different Stripe
            // API version than this SDK expects — fall back to
            // deserializeUnsafe(), which ignores that mismatch (safe here
            // since we only read a handful of stable top-level fields).
            try {
                stripeObject = deserializer.deserializeUnsafe();
            } catch (EventDataObjectDeserializationException e) {
                log.warn("Could not deserialize Stripe event {} data object", event.getId(), e);
                return null;
            }
        }
        return stripeObject instanceof Session session ? session : null;
    }
}
