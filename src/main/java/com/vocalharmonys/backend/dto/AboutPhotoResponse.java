package com.vocalharmonys.backend.dto;

import com.vocalharmonys.backend.entity.AboutPhoto;

public record AboutPhotoResponse(Long id, String imageUrl, int displayOrder) {

    public static AboutPhotoResponse from(AboutPhoto photo) {
        return new AboutPhotoResponse(photo.getId(), photo.getImageUrl(), photo.getDisplayOrder());
    }
}
