package com.vocalharmonys.backend.repository;

import com.vocalharmonys.backend.entity.HomeBanner;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeBannerRepository extends JpaRepository<HomeBanner, Long> {

    List<HomeBanner> findAllByOrderByDisplayOrderAsc();
}
