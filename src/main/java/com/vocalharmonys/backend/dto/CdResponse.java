package com.vocalharmonys.backend.dto;

import com.vocalharmonys.backend.entity.Cd;
import java.math.BigDecimal;

public record CdResponse(
        Long id,
        String title,
        int releaseYear,
        BigDecimal price,
        String description,
        String imageUrl
) {

    public static CdResponse from(Cd cd) {
        return new CdResponse(cd.getId(), cd.getTitle(), cd.getReleaseYear(), cd.getPrice(), cd.getDescription(), cd.getImageUrl());
    }
}
