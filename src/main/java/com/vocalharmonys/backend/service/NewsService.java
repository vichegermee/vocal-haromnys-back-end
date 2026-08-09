package com.vocalharmonys.backend.service;

import com.vocalharmonys.backend.dto.NewsItemRequest;
import com.vocalharmonys.backend.dto.NewsItemResponse;
import com.vocalharmonys.backend.entity.NewsItem;
import com.vocalharmonys.backend.exception.ResourceNotFoundException;
import com.vocalharmonys.backend.repository.NewsItemRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/** Backs the "Actualités" sidebar. */
@Service
public class NewsService {

    private final NewsItemRepository newsItemRepository;

    public NewsService(NewsItemRepository newsItemRepository) {
        this.newsItemRepository = newsItemRepository;
    }

    public List<NewsItemResponse> listAll() {
        return newsItemRepository.findAllByOrderByIdDesc().stream().map(NewsItemResponse::from).toList();
    }

    public NewsItemResponse create(NewsItemRequest request) {
        NewsItem item = new NewsItem();
        item.setItemDate(request.itemDate());
        item.setTitle(request.title());
        return NewsItemResponse.from(newsItemRepository.save(item));
    }

    public NewsItemResponse update(Long id, NewsItemRequest request) {
        NewsItem item = newsItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Actualité introuvable : " + id));
        item.setItemDate(request.itemDate());
        item.setTitle(request.title());
        return NewsItemResponse.from(newsItemRepository.save(item));
    }

    public void delete(Long id) {
        if (!newsItemRepository.existsById(id)) {
            throw new ResourceNotFoundException("Actualité introuvable : " + id);
        }
        newsItemRepository.deleteById(id);
    }
}
