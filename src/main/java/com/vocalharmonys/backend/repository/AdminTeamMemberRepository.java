package com.vocalharmonys.backend.repository;

import com.vocalharmonys.backend.entity.AdminTeamMember;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminTeamMemberRepository extends JpaRepository<AdminTeamMember, Long> {

    List<AdminTeamMember> findAllByOrderByDisplayOrderAsc();
}
