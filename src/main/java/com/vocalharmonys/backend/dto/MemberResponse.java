package com.vocalharmonys.backend.dto;

import com.vocalharmonys.backend.entity.Member;

public record MemberResponse(Long id, String username, String fullName, String role) {

    public static MemberResponse from(Member member) {
        return new MemberResponse(member.getId(), member.getUsername(), member.getFullName(), member.getRole().name());
    }
}
