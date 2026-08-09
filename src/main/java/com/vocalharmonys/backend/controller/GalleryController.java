package com.vocalharmonys.backend.controller;

import com.vocalharmonys.backend.dto.GalleryPhotoRequest;
import com.vocalharmonys.backend.dto.GalleryPhotoResponse;
import com.vocalharmonys.backend.dto.GalleryVideoRequest;
import com.vocalharmonys.backend.dto.GalleryVideoResponse;
import com.vocalharmonys.backend.service.GalleryService;
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

/** The "Galerie" page's photo carousel and video section. */
@RestController
@RequestMapping("/api/gallery")
public class GalleryController {

    private final GalleryService galleryService;

    public GalleryController(GalleryService galleryService) {
        this.galleryService = galleryService;
    }

    @GetMapping("/photos")
    public List<GalleryPhotoResponse> listPhotos() {
        return galleryService.listPhotos();
    }

    @PostMapping("/photos")
    public GalleryPhotoResponse createPhoto(@Valid @RequestBody GalleryPhotoRequest request) {
        return galleryService.createPhoto(request);
    }

    @PutMapping("/photos/{id}")
    public GalleryPhotoResponse updatePhoto(@PathVariable Long id, @Valid @RequestBody GalleryPhotoRequest request) {
        return galleryService.updatePhoto(id, request);
    }

    @DeleteMapping("/photos/{id}")
    public void deletePhoto(@PathVariable Long id) {
        galleryService.deletePhoto(id);
    }

    @GetMapping("/videos")
    public List<GalleryVideoResponse> listVideos() {
        return galleryService.listVideos();
    }

    @PostMapping("/videos")
    public GalleryVideoResponse createVideo(@Valid @RequestBody GalleryVideoRequest request) {
        return galleryService.createVideo(request);
    }

    @PutMapping("/videos/{id}")
    public GalleryVideoResponse updateVideo(@PathVariable Long id, @Valid @RequestBody GalleryVideoRequest request) {
        return galleryService.updateVideo(id, request);
    }

    @DeleteMapping("/videos/{id}")
    public void deleteVideo(@PathVariable Long id) {
        galleryService.deleteVideo(id);
    }
}
