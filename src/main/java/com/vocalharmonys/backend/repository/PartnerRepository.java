package com.vocalharmonys.backend.repository;

import com.vocalharmonys.backend.entity.Partner;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartnerRepository extends JpaRepository<Partner, Long> {

    List<Partner> findAllByOrderByDisplayOrderAsc();
}
