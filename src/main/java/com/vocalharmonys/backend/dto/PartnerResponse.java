package com.vocalharmonys.backend.dto;

import com.vocalharmonys.backend.entity.Partner;

public record PartnerResponse(Long id, String label, String imageUrl, int displayOrder) {

    public static PartnerResponse from(Partner partner) {
        return new PartnerResponse(partner.getId(), partner.getLabel(), partner.getImageUrl(), partner.getDisplayOrder());
    }
}
