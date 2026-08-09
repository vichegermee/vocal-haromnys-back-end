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
        int quantity,
        String message,
        String status,
        LocalDateTime createdAt
) {

    public static CdOrderResponse from(CdOrder order) {
        return new CdOrderResponse(
                order.getId(),
                order.getCdTitleSnapshot(),
                order.getUnitPriceSnapshot(),
                order.getCustomerName(),
                order.getCustomerEmail(),
                order.getQuantity(),
                order.getMessage(),
                order.getStatus().name(),
                order.getCreatedAt()
        );
    }
}
