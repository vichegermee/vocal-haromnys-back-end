package com.vocalharmonys.backend.service;

import com.vocalharmonys.backend.dto.PartnerRequest;
import com.vocalharmonys.backend.dto.PartnerResponse;
import com.vocalharmonys.backend.entity.Partner;
import com.vocalharmonys.backend.exception.ResourceNotFoundException;
import com.vocalharmonys.backend.repository.PartnerRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/** Backs the partner logos shown at the bottom of every page. */
@Service
public class PartnerService {

    private final PartnerRepository partnerRepository;

    public PartnerService(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    public List<PartnerResponse> listAll() {
        return partnerRepository.findAllByOrderByDisplayOrderAsc().stream().map(PartnerResponse::from).toList();
    }

    public PartnerResponse create(PartnerRequest request) {
        Partner partner = new Partner();
        partner.setLabel(request.label());
        partner.setImageUrl(request.imageUrl());
        partner.setDisplayOrder(request.displayOrder());
        return PartnerResponse.from(partnerRepository.save(partner));
    }

    public PartnerResponse update(Long id, PartnerRequest request) {
        Partner partner = partnerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Partenaire introuvable : " + id));
        partner.setLabel(request.label());
        partner.setImageUrl(request.imageUrl());
        partner.setDisplayOrder(request.displayOrder());
        return PartnerResponse.from(partnerRepository.save(partner));
    }

    public void delete(Long id) {
        if (!partnerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Partenaire introuvable : " + id);
        }
        partnerRepository.deleteById(id);
    }
}
