package com.vocalharmonys.backend.controller;

import com.vocalharmonys.backend.dto.NewsItemRequest;
import com.vocalharmonys.backend.dto.NewsItemResponse;
import com.vocalharmonys.backend.service.NewsService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** The "Actualités" sidebar. */
@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping
    public List<NewsItemResponse> listAll() {
        return newsService.listAll();
    }

    @PostMapping
    public NewsItemResponse create(@Valid @RequestBody NewsItemRequest request) {
        return newsService.create(request);
    }

    @PutMapping("/{id}")
    public NewsItemResponse update(@PathVariable Long id, @Valid @RequestBody NewsItemRequest request) {
        return newsService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        newsService.delete(id);
    }
}
