package com.vocalharmonys.backend.controller;

import com.vocalharmonys.backend.dto.ChoristerRequest;
import com.vocalharmonys.backend.dto.ChoristerResponse;
import com.vocalharmonys.backend.service.ChoristerService;
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
 * The "Choristes" page. GET is public (see SecurityConfig); creating,
 * editing, or removing a chorister needs a logged-in member.
 */
@RestController
@RequestMapping("/api/choristers")
public class ChoristerController {

    private final ChoristerService choristerService;

    public ChoristerController(ChoristerService choristerService) {
        this.choristerService = choristerService;
    }

    @GetMapping
    public List<ChoristerResponse> listAll() {
        return choristerService.listAll();
    }

    @PostMapping
    public ChoristerResponse create(@Valid @RequestBody ChoristerRequest request) {
        return choristerService.create(request);
    }

    @PutMapping("/{id}")
    public ChoristerResponse update(@PathVariable Long id, @Valid @RequestBody ChoristerRequest request) {
        return choristerService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        choristerService.delete(id);
    }
}
