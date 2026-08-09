package com.vocalharmonys.backend.repository;

import com.vocalharmonys.backend.entity.Cd;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CdRepository extends JpaRepository<Cd, Long> {
}
