package com.vocalharmonys.backend.service;

import com.vocalharmonys.backend.dto.JoinApplicationRequest;
import com.vocalharmonys.backend.dto.JoinApplicationResponse;
import com.vocalharmonys.backend.entity.JoinApplication;
import com.vocalharmonys.backend.repository.JoinApplicationRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/** Backs the "Rejoindre la chorale" form on the Contact page. */
@Service
public class JoinApplicationService {

    private final JoinApplicationRepository joinApplicationRepository;

    public JoinApplicationService(JoinApplicationRepository joinApplicationRepository) {
        this.joinApplicationRepository = joinApplicationRepository;
    }

    public List<JoinApplicationResponse> listAll() {
        return joinApplicationRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(JoinApplicationResponse::from)
                .toList();
    }

    public JoinApplicationResponse create(JoinApplicationRequest request) {
        JoinApplication application = new JoinApplication();
        application.setVoicePart(request.voicePart());
        application.setExperience(request.experience());
        application.setAvailability(request.availability());
        application.setAudioLink(request.audioLink());
        application.setMotivation(request.motivation());
        application.setApplicantName(request.applicantName());
        application.setApplicantEmail(request.applicantEmail());
        return JoinApplicationResponse.from(joinApplicationRepository.save(application));
    }
}
