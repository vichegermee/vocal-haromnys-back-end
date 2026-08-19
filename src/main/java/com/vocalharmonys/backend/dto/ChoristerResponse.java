package com.vocalharmonys.backend.dto;

import com.vocalharmonys.backend.entity.Chorister;

public record ChoristerResponse(
        Long id,
        String name,
        String voicePart,
        String description,
        String imageUrl,
        int displayOrder
) {

    public static ChoristerResponse from(Chorister chorister) {
        return new ChoristerResponse(
                chorister.getId(),
                chorister.getName(),
                chorister.getVoicePart(),
                chorister.getDescription(),
                chorister.getImageUrl(),
                chorister.getDisplayOrder()
        );
    }
}
