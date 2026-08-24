package com.vocalharmonys.backend.repository;

import com.vocalharmonys.backend.entity.SupportTeamMember;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupportTeamMemberRepository extends JpaRepository<SupportTeamMember, Long> {

    List<SupportTeamMember> findAllByOrderByDisplayOrderAsc();
}
