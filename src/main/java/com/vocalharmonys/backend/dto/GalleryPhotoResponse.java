package com.vocalharmonys.backend.dto;

import com.vocalharmonys.backend.entity.GalleryPhoto;

public record GalleryPhotoResponse(Long id, String label, String imageUrl, int displayOrder) {

    public static GalleryPhotoResponse from(GalleryPhoto photo) {
        return new GalleryPhotoResponse(photo.getId(), photo.getLabel(), photo.getImageUrl(), photo.getDisplayOrder());
    }
}
