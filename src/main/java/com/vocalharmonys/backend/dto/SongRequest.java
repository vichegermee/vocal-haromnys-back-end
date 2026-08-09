package com.vocalharmonys.backend.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record SongRequest(
        @NotBlank String title,
        @NotBlank String voicing,
        @NotBlank String musicalKey,
        @NotNull @Valid List<AudioTrackRequest> tracks
) {
}
