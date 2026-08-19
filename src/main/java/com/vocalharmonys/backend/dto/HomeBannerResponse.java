package com.vocalharmonys.backend.dto;

import com.vocalharmonys.backend.entity.HomeBanner;

public record HomeBannerResponse(Long id, String imageUrl, int displayOrder) {

    public static HomeBannerResponse from(HomeBanner banner) {
        return new HomeBannerResponse(banner.getId(), banner.getImageUrl(), banner.getDisplayOrder());
    }
}
