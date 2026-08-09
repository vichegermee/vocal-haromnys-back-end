package com.vocalharmonys.backend.dto;

import com.vocalharmonys.backend.entity.TrackType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AudioTrackRequest(
        @NotNull TrackType trackType,
        @NotBlank String fileUrl
) {
}
