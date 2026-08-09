package com.vocalharmonys.backend.service;

import com.vocalharmonys.backend.dto.CdRequest;
import com.vocalharmonys.backend.dto.CdResponse;
import com.vocalharmonys.backend.entity.Cd;
import com.vocalharmonys.backend.exception.ResourceNotFoundException;
import com.vocalharmonys.backend.repository.CdRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/** Backs the "Boutique" catalog (the CD_orders themselves live in {@code CdOrderService}). */
@Service
public class CdService {

    private final CdRepository cdRepository;

    public CdService(CdRepository cdRepository) {
        this.cdRepository = cdRepository;
    }

    public List<CdResponse> listAll() {
        return cdRepository.findAll().stream().map(CdResponse::from).toList();
    }

    public Cd findOrThrow(Long id) {
        return cdRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CD introuvable : " + id));
    }

    public CdResponse create(CdRequest request) {
        Cd cd = new Cd();
        applyRequest(cd, request);
        return CdResponse.from(cdRepository.save(cd));
    }

    public CdResponse update(Long id, CdRequest request) {
        Cd cd = findOrThrow(id);
        applyRequest(cd, request);
        return CdResponse.from(cdRepository.save(cd));
    }

    public void delete(Long id) {
        if (!cdRepository.existsById(id)) {
            throw new ResourceNotFoundException("CD introuvable : " + id);
        }
        cdRepository.deleteById(id);
    }

    private void applyRequest(Cd cd, CdRequest request) {
        cd.setTitle(request.title());
        cd.setReleaseYear(request.releaseYear());
        cd.setPrice(request.price());
        cd.setDescription(request.description());
        cd.setImageUrl(request.imageUrl());
    }
}
