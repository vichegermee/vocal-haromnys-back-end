package com.vocalharmonys.backend.controller;

import com.vocalharmonys.backend.dto.LoginRequest;
import com.vocalharmonys.backend.dto.LoginResponse;
import com.vocalharmonys.backend.dto.MemberResponse;
import com.vocalharmonys.backend.security.MemberPrincipal;
import com.vocalharmonys.backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Login and "who am I". See SecurityConfig for the exact rule: only
 * POST /api/auth/login is public, GET /api/auth/me needs a valid token.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @GetMapping("/me")
    public MemberResponse me(@AuthenticationPrincipal MemberPrincipal principal) {
        return authService.currentMember(principal);
    }
}
