package com.vocalharmonys.backend.dto;

import com.vocalharmonys.backend.entity.SupportTeamMember;

public record SupportTeamMemberResponse(
        Long id,
        String firstName,
        String lastName,
        String title,
        String photoFilename,
        int displayOrder
) {

    public static SupportTeamMemberResponse from(SupportTeamMember member) {
        return new SupportTeamMemberResponse(
                member.getId(),
                member.getFirstName(),
                member.getLastName(),
                member.getTitle(),
                member.getPhotoFilename(),
                member.getDisplayOrder()
        );
    }
}
