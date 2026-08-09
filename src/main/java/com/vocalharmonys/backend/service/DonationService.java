package com.vocalharmonys.backend.service;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.vocalharmonys.backend.dto.DonationCheckoutResponse;
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
    private final StripeCheckoutService stripeCheckoutService;

    public DonationService(DonationRepository donationRepository, StripeCheckoutService stripeCheckoutService) {
        this.donationRepository = donationRepository;
        this.stripeCheckoutService = stripeCheckoutService;
    }

    public List<DonationResponse> listAll() {
        return donationRepository.findAllByOrderByCreatedAtDesc().stream().map(DonationResponse::from).toList();
    }

    /**
     * Saves a PENDING donation row (so there's a record even if the visitor
     * abandons checkout), creates the matching Stripe Checkout Session, then
     * links the two together. {@link com.vocalharmonys.backend.controller.StripeWebhookController}
     * is what later flips this to PAID once Stripe confirms payment.
     */
    public DonationCheckoutResponse createCheckoutSession(DonationRequest request) {
        Donation donation = new Donation();
        donation.setAmount(request.amount());
        donation.setDonorName(request.donorName());
        donation.setDonorEmail(request.donorEmail());
        donation = donationRepository.save(donation);

        Session session;
        try {
            session = stripeCheckoutService.createCheckoutSession(donation);
        } catch (StripeException e) {
            throw new RuntimeException("Impossible de créer la session de paiement Stripe.", e);
        }

        donation.setStripeCheckoutSessionId(session.getId());
        donationRepository.save(donation);

        return new DonationCheckoutResponse(donation.getId(), session.getUrl());
    }
}
