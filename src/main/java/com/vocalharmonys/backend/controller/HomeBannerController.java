package com.vocalharmonys.backend.controller;

import com.vocalharmonys.backend.dto.HomeBannerRequest;
import com.vocalharmonys.backend.dto.HomeBannerResponse;
import com.vocalharmonys.backend.service.HomeBannerService;
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

/** The rotating hero carousel at the top of the homepage. */
@RestController
@RequestMapping("/api/home-banners")
public class HomeBannerController {

    private final HomeBannerService homeBannerService;

    public HomeBannerController(HomeBannerService homeBannerService) {
        this.homeBannerService = homeBannerService;
    }

    @GetMapping
    public List<HomeBannerResponse> listAll() {
        return homeBannerService.listAll();
    }

    @PostMapping
    public HomeBannerResponse create(@Valid @RequestBody HomeBannerRequest request) {
        return homeBannerService.create(request);
    }

    @PutMapping("/{id}")
    public HomeBannerResponse update(@PathVariable Long id, @Valid @RequestBody HomeBannerRequest request) {
        return homeBannerService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        homeBannerService.delete(id);
    }
}
