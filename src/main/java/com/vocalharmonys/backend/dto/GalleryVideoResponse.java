package com.vocalharmonys.backend.dto;

import com.vocalharmonys.backend.entity.GalleryVideo;

public record GalleryVideoResponse(Long id, String title, String youtubeId, int displayOrder) {

    public static GalleryVideoResponse from(GalleryVideo video) {
        return new GalleryVideoResponse(video.getId(), video.getTitle(), video.getYoutubeId(), video.getDisplayOrder());
    }
}
