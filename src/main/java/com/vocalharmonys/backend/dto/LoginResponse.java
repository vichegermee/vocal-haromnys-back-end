package com.vocalharmonys.backend.dto;

public record LoginResponse(String token, MemberResponse member) {
}
