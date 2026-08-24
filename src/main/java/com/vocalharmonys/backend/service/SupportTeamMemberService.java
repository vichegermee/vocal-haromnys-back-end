package com.vocalharmonys.backend.service;

import com.vocalharmonys.backend.dto.SupportTeamMemberRequest;
import com.vocalharmonys.backend.dto.SupportTeamMemberResponse;
import com.vocalharmonys.backend.entity.SupportTeamMember;
import com.vocalharmonys.backend.exception.ResourceNotFoundException;
import com.vocalharmonys.backend.repository.SupportTeamMemberRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/** Backs "L'équipe d'accompagnement" section on the About page. */
@Service
public class SupportTeamMemberService {

    private final SupportTeamMemberRepository supportTeamMemberRepository;

    public SupportTeamMemberService(SupportTeamMemberRepository supportTeamMemberRepository) {
        this.supportTeamMemberRepository = supportTeamMemberRepository;
    }

    public List<SupportTeamMemberResponse> listAll() {
        return supportTeamMemberRepository.findAllByOrderByDisplayOrderAsc().stream()
                .map(SupportTeamMemberResponse::from)
                .toList();
    }

    public SupportTeamMemberResponse create(SupportTeamMemberRequest request) {
        SupportTeamMember member = new SupportTeamMember();
        applyRequest(member, request);
        return SupportTeamMemberResponse.from(supportTeamMemberRepository.save(member));
    }

    public SupportTeamMemberResponse update(Long id, SupportTeamMemberRequest request) {
        SupportTeamMember member = supportTeamMemberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Membre de l'équipe introuvable : " + id));
        applyRequest(member, request);
        return SupportTeamMemberResponse.from(supportTeamMemberRepository.save(member));
    }

    public void delete(Long id) {
        if (!supportTeamMemberRepository.existsById(id)) {
            throw new ResourceNotFoundException("Membre de l'équipe introuvable : " + id);
        }
        supportTeamMemberRepository.deleteById(id);
    }

    private void applyRequest(SupportTeamMember member, SupportTeamMemberRequest request) {
        member.setFirstName(request.firstName());
        member.setLastName(request.lastName());
        member.setTitle(request.title());
        member.setPhotoFilename(request.photoFilename());
        member.setDisplayOrder(request.displayOrder());
    }
}
