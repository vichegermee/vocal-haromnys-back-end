package com.vocalharmonys.backend.service;

import com.vocalharmonys.backend.dto.DonationRequest;
import com.vocalharmonys.backend.dto.DonationResponse;
import com.vocalharmonys.backend.entity.Donation;
import com.vocalharmonys.backend.repository.DonationRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/** Backs the "Faire un don" page. */
@Service
public class DonationService {

    private final DonationRepository donationRepository;

    public DonationService(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    public List<DonationResponse> listAll() {
        return donationRepository.findAllByOrderByCreatedAtDesc().stream().map(DonationResponse::from).toList();
    }

    public DonationResponse create(DonationRequest request) {
        Donation donation = new Donation();
        donation.setAmount(request.amount());
        donation.setDonorName(request.donorName());
        donation.setDonorEmail(request.donorEmail());
        return DonationResponse.from(donationRepository.save(donation));
    }
}
