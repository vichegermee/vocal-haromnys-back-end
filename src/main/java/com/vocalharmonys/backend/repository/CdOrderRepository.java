package com.vocalharmonys.backend.repository;

import com.vocalharmonys.backend.entity.CdOrder;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CdOrderRepository extends JpaRepository<CdOrder, Long> {

    List<CdOrder> findAllByOrderByCreatedAtDesc();

    Optional<CdOrder> findByStripeCheckoutSessionId(String stripeCheckoutSessionId);
}
