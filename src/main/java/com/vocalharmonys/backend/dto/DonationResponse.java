package com.vocalharmonys.backend.dto;

import com.vocalharmonys.backend.entity.Donation;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DonationResponse(
        Long id,
        BigDecimal amount,
        String donorName,
        String donorEmail,
        String status,
        String paymentStatus,
        LocalDateTime paidAt,
        LocalDateTime createdAt
) {

    public static DonationResponse from(Donation donation) {
        return new DonationResponse(
                donation.getId(),
                donation.getAmount(),
                donation.getDonorName(),
                donation.getDonorEmail(),
                donation.getStatus().name(),
                donation.getPaymentStatus().name(),
                donation.getPaidAt(),
                donation.getCreatedAt()
        );
    }
}
