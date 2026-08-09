package com.vocalharmonys.backend.dto;

import com.vocalharmonys.backend.entity.Chorister;
import com.vocalharmonys.backend.entity.VoicePart;

public record ChoristerResponse(
        Long id,
        String name,
        VoicePart voicePart,
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
