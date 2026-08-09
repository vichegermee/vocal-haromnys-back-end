package com.vocalharmonys.backend.repository;

import com.vocalharmonys.backend.entity.JoinApplication;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JoinApplicationRepository extends JpaRepository<JoinApplication, Long> {

    List<JoinApplication> findAllByOrderByCreatedAtDesc();
}
