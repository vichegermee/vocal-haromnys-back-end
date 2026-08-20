package com.vocalharmonys.backend.dto;

import com.vocalharmonys.backend.entity.AdminTeamMember;

public record AdminTeamMemberResponse(
        Long id,
        String firstName,
        String lastName,
        String title,
        String photoFilename,
        int displayOrder
) {

    public static AdminTeamMemberResponse from(AdminTeamMember member) {
        return new AdminTeamMemberResponse(
                member.getId(),
                member.getFirstName(),
                member.getLastName(),
                member.getTitle(),
                member.getPhotoFilename(),
                member.getDisplayOrder()
        );
    }
}
