package com.vocalharmonys.backend.service;

import com.vocalharmonys.backend.dto.HomeBannerRequest;
import com.vocalharmonys.backend.dto.HomeBannerResponse;
import com.vocalharmonys.backend.entity.HomeBanner;
import com.vocalharmonys.backend.exception.ResourceNotFoundException;
import com.vocalharmonys.backend.repository.HomeBannerRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/** Backs the rotating hero carousel at the top of the homepage. */
@Service
public class HomeBannerService {

    private final HomeBannerRepository homeBannerRepository;

    public HomeBannerService(HomeBannerRepository homeBannerRepository) {
        this.homeBannerRepository = homeBannerRepository;
    }

    public List<HomeBannerResponse> listAll() {
        return homeBannerRepository.findAllByOrderByDisplayOrderAsc().stream().map(HomeBannerResponse::from).toList();
    }

    public HomeBannerResponse create(HomeBannerRequest request) {
        HomeBanner banner = new HomeBanner();
        banner.setImageUrl(request.imageUrl());
        banner.setDisplayOrder(request.displayOrder());
        return HomeBannerResponse.from(homeBannerRepository.save(banner));
    }

    public HomeBannerResponse update(Long id, HomeBannerRequest request) {
        HomeBanner banner = homeBannerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bannière introuvable : " + id));
        banner.setImageUrl(request.imageUrl());
        banner.setDisplayOrder(request.displayOrder());
        return HomeBannerResponse.from(homeBannerRepository.save(banner));
    }

    public void delete(Long id) {
        if (!homeBannerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Bannière introuvable : " + id);
        }
        homeBannerRepository.deleteById(id);
    }
}
