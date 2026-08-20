package com.vocalharmonys.backend.service;

import com.vocalharmonys.backend.dto.AdminTeamMemberRequest;
import com.vocalharmonys.backend.dto.AdminTeamMemberResponse;
import com.vocalharmonys.backend.entity.AdminTeamMember;
import com.vocalharmonys.backend.exception.ResourceNotFoundException;
import com.vocalharmonys.backend.repository.AdminTeamMemberRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/** Backs the "Notre équipe" section on the About page. */
@Service
public class AdminTeamMemberService {

    private final AdminTeamMemberRepository adminTeamMemberRepository;

    public AdminTeamMemberService(AdminTeamMemberRepository adminTeamMemberRepository) {
        this.adminTeamMemberRepository = adminTeamMemberRepository;
    }

    public List<AdminTeamMemberResponse> listAll() {
        return adminTeamMemberRepository.findAllByOrderByDisplayOrderAsc().stream()
                .map(AdminTeamMemberResponse::from)
                .toList();
    }

    public AdminTeamMemberResponse create(AdminTeamMemberRequest request) {
        AdminTeamMember member = new AdminTeamMember();
        applyRequest(member, request);
        return AdminTeamMemberResponse.from(adminTeamMemberRepository.save(member));
    }

    public AdminTeamMemberResponse update(Long id, AdminTeamMemberRequest request) {
        AdminTeamMember member = adminTeamMemberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Membre de l'équipe introuvable : " + id));
        applyRequest(member, request);
        return AdminTeamMemberResponse.from(adminTeamMemberRepository.save(member));
    }

    public void delete(Long id) {
        if (!adminTeamMemberRepository.existsById(id)) {
            throw new ResourceNotFoundException("Membre de l'équipe introuvable : " + id);
        }
        adminTeamMemberRepository.deleteById(id);
    }

    private void applyRequest(AdminTeamMember member, AdminTeamMemberRequest request) {
        member.setFirstName(request.firstName());
        member.setLastName(request.lastName());
        member.setTitle(request.title());
        member.setPhotoFilename(request.photoFilename());
        member.setDisplayOrder(request.displayOrder());
    }
}
