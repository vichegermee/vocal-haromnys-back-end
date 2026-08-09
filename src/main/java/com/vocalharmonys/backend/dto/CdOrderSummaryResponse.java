package com.vocalharmonys.backend.dto;

import com.vocalharmonys.backend.entity.CdOrder;
import java.math.BigDecimal;

/**
 * The order confirmation shown after returning from Stripe Checkout — looked
 * up by Stripe session id (an unguessable token), not by the order's own
 * sequential id, so a visitor can only ever fetch their own just-placed
 * order. Deliberately excludes name/email/address/phone: the summary only
 * needs to show what was bought, not repeat back personal data.
 */
public record CdOrderSummaryResponse(
        String cdTitle,
        int quantity,
        BigDecimal unitPrice,
        String shippingOptionLabel,
        BigDecimal shippingCost,
        BigDecimal totalAmount,
        String paymentStatus
) {

    public static CdOrderSummaryResponse from(CdOrder order) {
        return new CdOrderSummaryResponse(
                order.getCdTitleSnapshot(),
                order.getQuantity(),
                order.getUnitPriceSnapshot(),
                order.getShippingOption().getLabel(),
                order.getShippingCost(),
                order.getTotalAmount(),
                order.getPaymentStatus().name()
        );
    }
}
