package com.vocalharmonys.backend.service;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.vocalharmonys.backend.entity.CdOrder;
import com.vocalharmonys.backend.entity.Donation;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Talks to Stripe only — creating the hosted Checkout Session a donor gets
 * redirected to. Deliberately never calls {@code addPaymentMethodType(...)}:
 * omitting it entirely is what lets Checkout auto-offer whatever payment
 * methods (card, Apple Pay, PayPal...) are enabled in the Stripe Dashboard,
 * with no code change needed here when more are turned on later.
 */
@Service
public class StripeCheckoutService {

    private final String frontendBaseUrl;

    public StripeCheckoutService(@Value("${app.frontend.base-url}") String frontendBaseUrl) {
        this.frontendBaseUrl = frontendBaseUrl;
    }

    public Session createCheckoutSession(Donation donation) throws StripeException {
        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(frontendBaseUrl + "/dons?checkout=success&session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl(frontendBaseUrl + "/dons?checkout=cancel")
                .setCustomerEmail(donation.getDonorEmail())
                .putMetadata("type", "donation")
                .putMetadata("donationId", donation.getId().toString())
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("eur")
                                .setUnitAmount(donation.getAmount().multiply(BigDecimal.valueOf(100)).longValueExact())
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("Don – Vocal Harmony's")
                                        .build())
                                .build())
                        .build())
                .build();

        return Session.create(params);
    }

    /** Two line items — the album (unit price × quantity) and shipping — so Stripe's own page shows the same breakdown as ours. */
    public Session createCdOrderCheckoutSession(CdOrder order) throws StripeException {
        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(frontendBaseUrl + "/boutique?checkout=success&session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl(frontendBaseUrl + "/boutique?checkout=cancel")
                .setCustomerEmail(order.getCustomerEmail())
                .putMetadata("type", "cd_order")
                .putMetadata("cdOrderId", order.getId().toString())
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity((long) order.getQuantity())
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("eur")
                                .setUnitAmount(order.getUnitPriceSnapshot().multiply(BigDecimal.valueOf(100)).longValueExact())
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName(order.getCdTitleSnapshot())
                                        .build())
                                .build())
                        .build())
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("eur")
                                .setUnitAmount(order.getShippingCost().multiply(BigDecimal.valueOf(100)).longValueExact())
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName(order.getShippingOption().getLabel())
                                        .build())
                                .build())
                        .build())
                .build();

        return Session.create(params);
    }
}
