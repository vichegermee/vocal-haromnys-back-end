package com.vocalharmonys.backend.repository;

import com.vocalharmonys.backend.entity.Donation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationRepository extends JpaRepository<Donation, Long> {

    List<Donation> findAllByOrderByCreatedAtDesc();

    Optional<Donation> findByStripeCheckoutSessionId(String stripeCheckoutSessionId);
}
