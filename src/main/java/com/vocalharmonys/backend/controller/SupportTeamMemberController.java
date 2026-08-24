package com.vocalharmonys.backend.controller;

import com.vocalharmonys.backend.dto.SupportTeamMemberRequest;
import com.vocalharmonys.backend.dto.SupportTeamMemberResponse;
import com.vocalharmonys.backend.service.SupportTeamMemberService;
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

/** "L'équipe d'accompagnement" section on the About page. */
@RestController
@RequestMapping("/api/support-team")
public class SupportTeamMemberController {

    private final SupportTeamMemberService supportTeamMemberService;

    public SupportTeamMemberController(SupportTeamMemberService supportTeamMemberService) {
        this.supportTeamMemberService = supportTeamMemberService;
    }

    @GetMapping
    public List<SupportTeamMemberResponse> listAll() {
        return supportTeamMemberService.listAll();
    }

    @PostMapping
    public SupportTeamMemberResponse create(@Valid @RequestBody SupportTeamMemberRequest request) {
        return supportTeamMemberService.create(request);
    }

    @PutMapping("/{id}")
    public SupportTeamMemberResponse update(@PathVariable Long id, @Valid @RequestBody SupportTeamMemberRequest request) {
        return supportTeamMemberService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        supportTeamMemberService.delete(id);
    }
}
