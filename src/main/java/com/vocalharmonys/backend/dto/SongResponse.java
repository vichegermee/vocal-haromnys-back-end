package com.vocalharmonys.backend.dto;

import com.vocalharmonys.backend.entity.Song;
import java.util.List;

public record SongResponse(
        Long id,
        String title,
        String voicing,
        String musicalKey,
        List<AudioTrackResponse> tracks
) {

    public static SongResponse from(Song song) {
        return new SongResponse(
                song.getId(),
                song.getTitle(),
                song.getVoicing(),
                song.getMusicalKey(),
                song.getTracks().stream().map(AudioTrackResponse::from).toList()
        );
    }
}
