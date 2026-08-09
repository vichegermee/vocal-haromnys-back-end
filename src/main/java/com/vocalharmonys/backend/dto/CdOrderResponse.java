package com.vocalharmonys.backend.dto;

import com.vocalharmonys.backend.entity.CdOrder;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CdOrderResponse(
        Long id,
        String cdTitleSnapshot,
        BigDecimal unitPriceSnapshot,
        String customerName,
        String customerEmail,
        String customerPhone,
        int quantity,
        String shippingStreet,
        String shippingPostalCode,
        String shippingCity,
        String shippingCountry,
        String shippingOption,
        BigDecimal shippingCost,
        BigDecimal totalAmount,
        String message,
        String status,
        String paymentStatus,
        LocalDateTime paidAt,
        LocalDateTime createdAt
) {

    public static CdOrderResponse from(CdOrder order) {
        return new CdOrderResponse(
                order.getId(),
                order.getCdTitleSnapshot(),
                order.getUnitPriceSnapshot(),
                order.getCustomerName(),
                order.getCustomerEmail(),
                order.getCustomerPhone(),
                order.getQuantity(),
                order.getShippingStreet(),
                order.getShippingPostalCode(),
                order.getShippingCity(),
                order.getShippingCountry(),
                order.getShippingOption().name(),
                order.getShippingCost(),
                order.getTotalAmount(),
                order.getMessage(),
                order.getStatus().name(),
                order.getPaymentStatus().name(),
                order.getPaidAt(),
                order.getCreatedAt()
        );
    }
}
