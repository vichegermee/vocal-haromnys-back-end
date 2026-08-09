package com.vocalharmonys.backend.repository;

import com.vocalharmonys.backend.entity.Donation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationRepository extends JpaRepository<Donation, Long> {

    List<Donation> findAllByOrderByCreatedAtDesc();
}
