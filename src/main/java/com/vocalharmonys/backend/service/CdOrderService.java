package com.vocalharmonys.backend.service;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.vocalharmonys.backend.dto.CdOrderCheckoutResponse;
import com.vocalharmonys.backend.dto.CdOrderRequest;
import com.vocalharmonys.backend.dto.CdOrderResponse;
import com.vocalharmonys.backend.dto.CdOrderSummaryResponse;
import com.vocalharmonys.backend.entity.Cd;
import com.vocalharmonys.backend.entity.CdOrder;
import com.vocalharmonys.backend.exception.ResourceNotFoundException;
import com.vocalharmonys.backend.repository.CdOrderRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;

/** Backs the "Commander" dialog on the Boutique page. */
@Service
public class CdOrderService {

    private final CdOrderRepository cdOrderRepository;
    private final CdService cdService;
    private final StripeCheckoutService stripeCheckoutService;

    public CdOrderService(
            CdOrderRepository cdOrderRepository,
            CdService cdService,
            StripeCheckoutService stripeCheckoutService
    ) {
        this.cdOrderRepository = cdOrderRepository;
        this.cdService = cdService;
        this.stripeCheckoutService = stripeCheckoutService;
    }

    public List<CdOrderResponse> listAll() {
        return cdOrderRepository.findAllByOrderByCreatedAtDesc().stream().map(CdOrderResponse::from).toList();
    }

    /**
     * Saves a PENDING order row (so there's a record even if the visitor
     * abandons checkout), creates the matching Stripe Checkout Session, then
     * links the two together. {@link com.vocalharmonys.backend.controller.StripeWebhookController}
     * is what later flips this to PAID once Stripe confirms payment.
     */
    public CdOrderCheckoutResponse createCheckoutSession(CdOrderRequest request) {
        Cd cd = cdService.findOrThrow(request.cdId());
        BigDecimal shippingCost = request.shippingOption().getCost();
        BigDecimal totalAmount = cd.getPrice()
                .multiply(BigDecimal.valueOf(request.quantity()))
                .add(shippingCost);

        CdOrder order = new CdOrder();
        order.setCd(cd);
        // Snapshot now, so a later price/title edit on the CD never rewrites
        // what this customer actually agreed to order.
        order.setCdTitleSnapshot(cd.getTitle());
        order.setUnitPriceSnapshot(cd.getPrice());
        order.setCustomerName(request.customerName());
        order.setCustomerEmail(request.customerEmail());
        order.setCustomerPhone(request.customerPhone());
        order.setQuantity(request.quantity());
        order.setShippingStreet(request.shippingStreet());
        order.setShippingPostalCode(request.shippingPostalCode());
        order.setShippingCity(request.shippingCity());
        order.setShippingCountry(request.shippingCountry());
        order.setShippingOption(request.shippingOption());
        order.setShippingCost(shippingCost);
        order.setTotalAmount(totalAmount);
        order.setMessage(request.message());
        order = cdOrderRepository.save(order);

        Session session;
        try {
            session = stripeCheckoutService.createCdOrderCheckoutSession(order);
        } catch (StripeException e) {
            throw new RuntimeException("Impossible de créer la session de paiement Stripe.", e);
        }

        order.setStripeCheckoutSessionId(session.getId());
        cdOrderRepository.save(order);

        return new CdOrderCheckoutResponse(order.getId(), session.getUrl());
    }

    /** Looked up by Stripe session id (see CdOrderSummaryResponse for why, not by the order's own sequential id). */
    public CdOrderSummaryResponse findSummaryByStripeSessionId(String stripeCheckoutSessionId) {
        CdOrder order = cdOrderRepository.findByStripeCheckoutSessionId(stripeCheckoutSessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Commande introuvable."));
        return CdOrderSummaryResponse.from(order);
    }
}
