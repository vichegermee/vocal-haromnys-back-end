package com.vocalharmonys.backend.controller;

import com.vocalharmonys.backend.dto.SongRequest;
import com.vocalharmonys.backend.dto.SongResponse;
import com.vocalharmonys.backend.service.SongService;
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

/**
 * The répertoire ("Répertoire de répétition"). Unlike the other showcase
 * content, GET here is NOT public — SecurityConfig only allow-lists specific
 * endpoints, so /api/songs falls through to "must be logged in", matching
 * the gated page on the frontend.
 */
@RestController
@RequestMapping("/api/songs")
public class SongController {

    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping
    public List<SongResponse> listAll() {
        return songService.listAll();
    }

    @PostMapping
    public SongResponse create(@Valid @RequestBody SongRequest request) {
        return songService.create(request);
    }

    @PutMapping("/{id}")
    public SongResponse update(@PathVariable Long id, @Valid @RequestBody SongRequest request) {
        return songService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        songService.delete(id);
    }
}
