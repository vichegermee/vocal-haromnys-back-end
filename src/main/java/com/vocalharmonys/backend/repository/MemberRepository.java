package com.vocalharmonys.backend.repository;

import com.vocalharmonys.backend.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsernameIgnoreCase(String username);

    boolean existsByUsernameIgnoreCase(String username);
}
