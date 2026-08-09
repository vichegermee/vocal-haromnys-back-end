package com.vocalharmonys.backend.service;

import com.vocalharmonys.backend.dto.LoginRequest;
import com.vocalharmonys.backend.dto.LoginResponse;
import com.vocalharmonys.backend.dto.MemberResponse;
import com.vocalharmonys.backend.security.JwtService;
import com.vocalharmonys.backend.security.MemberPrincipal;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

/**
 * Backs POST /api/auth/login and GET /api/auth/me. The actual "is this
 * password right" check happens inside {@code authenticationManager.authenticate}
 * — it delegates to {@code MemberUserDetailsService} + the {@code
 * PasswordEncoder} bean and throws {@code BadCredentialsException} on a
 * mismatch, which {@code GlobalExceptionHandler} turns into a 401.
 */
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        MemberPrincipal principal = (MemberPrincipal) authentication.getPrincipal();
        String token = jwtService.generateToken(principal.getUsername());

        return new LoginResponse(token, MemberResponse.from(principal.member()));
    }

    public MemberResponse currentMember(MemberPrincipal principal) {
        return MemberResponse.from(principal.member());
    }
}
