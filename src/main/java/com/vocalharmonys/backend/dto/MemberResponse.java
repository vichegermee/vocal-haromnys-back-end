package com.vocalharmonys.backend.dto;

import com.vocalharmonys.backend.entity.Member;

// Deliberately has no password/passwordHash field, on this or any other
// response record in this package — the plaintext password only ever
// travels through the credentials email, never back to a browser.
public record MemberResponse(Long id, String username, String fullName, String email, String role) {

    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getUsername(),
                member.getFullName(),
                member.getEmail(),
                member.getRole().name()
        );
    }
}
