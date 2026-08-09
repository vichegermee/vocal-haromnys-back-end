package com.vocalharmonys.backend.dto;

import com.vocalharmonys.backend.entity.AudioTrack;
import com.vocalharmonys.backend.entity.TrackType;

public record AudioTrackResponse(Long id, TrackType trackType, String fileUrl) {

    public static AudioTrackResponse from(AudioTrack track) {
        return new AudioTrackResponse(track.getId(), track.getTrackType(), track.getFileUrl());
    }
}
