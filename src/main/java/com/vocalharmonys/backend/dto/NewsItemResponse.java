package com.vocalharmonys.backend.dto;

import com.vocalharmonys.backend.entity.NewsItem;

public record NewsItemResponse(Long id, String itemDate, String title) {

    public static NewsItemResponse from(NewsItem item) {
        return new NewsItemResponse(item.getId(), item.getItemDate(), item.getTitle());
    }
}
