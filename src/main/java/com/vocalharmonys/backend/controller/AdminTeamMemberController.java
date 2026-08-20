package com.vocalharmonys.backend.controller;

import com.vocalharmonys.backend.dto.AdminTeamMemberRequest;
import com.vocalharmonys.backend.dto.AdminTeamMemberResponse;
import com.vocalharmonys.backend.service.AdminTeamMemberService;
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

/** The "Notre équipe" section on the About page. */
@RestController
@RequestMapping("/api/admin-team")
public class AdminTeamMemberController {

    private final AdminTeamMemberService adminTeamMemberService;

    public AdminTeamMemberController(AdminTeamMemberService adminTeamMemberService) {
        this.adminTeamMemberService = adminTeamMemberService;
    }

    @GetMapping
    public List<AdminTeamMemberResponse> listAll() {
        return adminTeamMemberService.listAll();
    }

    @PostMapping
    public AdminTeamMemberResponse create(@Valid @RequestBody AdminTeamMemberRequest request) {
        return adminTeamMemberService.create(request);
    }

    @PutMapping("/{id}")
    public AdminTeamMemberResponse update(@PathVariable Long id, @Valid @RequestBody AdminTeamMemberRequest request) {
        return adminTeamMemberService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        adminTeamMemberService.delete(id);
    }
}
