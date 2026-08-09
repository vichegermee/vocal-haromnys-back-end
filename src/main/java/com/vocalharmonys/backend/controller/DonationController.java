package com.vocalharmonys.backend.controller;

import com.vocalharmonys.backend.dto.DonationRequest;
import com.vocalharmonys.backend.dto.DonationResponse;
import com.vocalharmonys.backend.service.DonationService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The "Faire un don" page. Submitting a donation is public; listing
 * donations back requires a logged-in member — see SecurityConfig.
 */
@RestController
@RequestMapping("/api/donations")
public class DonationController {

    private final DonationService donationService;

    public DonationController(DonationService donationService) {
        this.donationService = donationService;
    }

    @GetMapping
    public List<DonationResponse> listAll() {
        return donationService.listAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DonationResponse create(@Valid @RequestBody DonationRequest request) {
        return donationService.create(request);
    }
}
