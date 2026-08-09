package com.vocalharmonys.backend.dto;

import com.vocalharmonys.backend.entity.ShippingOption;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CdOrderRequest(
        @NotNull Long cdId,
        @NotBlank String customerName,
        @NotBlank @Email String customerEmail,
        @NotBlank String customerPhone,
        @Min(1) int quantity,
        @NotBlank String shippingStreet,
        @NotBlank String shippingPostalCode,
        @NotBlank String shippingCity,
        @NotBlank String shippingCountry,
        @NotNull ShippingOption shippingOption,
        String message
) {
}
