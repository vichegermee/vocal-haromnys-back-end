package com.vocalharmonys.backend.dto;

import com.vocalharmonys.backend.entity.JoinApplication;
import com.vocalharmonys.backend.entity.VoicePart;
import java.time.LocalDateTime;

public record JoinApplicationResponse(
        Long id,
        VoicePart voicePart,
        String experience,
        String availability,
        String audioLink,
        String motivation,
        String applicantName,
        String applicantEmail,
        String status,
        LocalDateTime createdAt
) {

    public static JoinApplicationResponse from(JoinApplication application) {
        return new JoinApplicationResponse(
                application.getId(),
                application.getVoicePart(),
                application.getExperience(),
                application.getAvailability(),
                application.getAudioLink(),
                application.getMotivation(),
                application.getApplicantName(),
                application.getApplicantEmail(),
                application.getStatus().name(),
                application.getCreatedAt()
        );
    }
}
