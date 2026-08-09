package com.vocalharmonys.backend.repository;

import com.vocalharmonys.backend.entity.CdOrder;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CdOrderRepository extends JpaRepository<CdOrder, Long> {

    List<CdOrder> findAllByOrderByCreatedAtDesc();
}
