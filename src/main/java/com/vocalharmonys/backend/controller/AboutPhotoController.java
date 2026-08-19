package com.vocalharmonys.backend.controller;

import com.vocalharmonys.backend.dto.AboutPhotoRequest;
import com.vocalharmonys.backend.dto.AboutPhotoResponse;
import com.vocalharmonys.backend.service.AboutPhotoService;
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

/** The rotating carousel next to the "Notre histoire" text on the About page. */
@RestController
@RequestMapping("/api/about-photos")
public class AboutPhotoController {

    private final AboutPhotoService aboutPhotoService;

    public AboutPhotoController(AboutPhotoService aboutPhotoService) {
        this.aboutPhotoService = aboutPhotoService;
    }

    @GetMapping
    public List<AboutPhotoResponse> listAll() {
        return aboutPhotoService.listAll();
    }

    @PostMapping
    public AboutPhotoResponse create(@Valid @RequestBody AboutPhotoRequest request) {
        return aboutPhotoService.create(request);
    }

    @PutMapping("/{id}")
    public AboutPhotoResponse update(@PathVariable Long id, @Valid @RequestBody AboutPhotoRequest request) {
        return aboutPhotoService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        aboutPhotoService.delete(id);
    }
}
