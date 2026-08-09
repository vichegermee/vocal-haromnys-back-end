package com.vocalharmonys.backend.controller;

import com.vocalharmonys.backend.dto.CdRequest;
import com.vocalharmonys.backend.dto.CdResponse;
import com.vocalharmonys.backend.service.CdService;
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

/** The "Boutique" album catalog. */
@RestController
@RequestMapping("/api/cds")
public class CdController {

    private final CdService cdService;

    public CdController(CdService cdService) {
        this.cdService = cdService;
    }

    @GetMapping
    public List<CdResponse> listAll() {
        return cdService.listAll();
    }

    @PostMapping
    public CdResponse create(@Valid @RequestBody CdRequest request) {
        return cdService.create(request);
    }

    @PutMapping("/{id}")
    public CdResponse update(@PathVariable Long id, @Valid @RequestBody CdRequest request) {
        return cdService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        cdService.delete(id);
    }
}
