package com.vocalharmonys.backend.controller;

import com.vocalharmonys.backend.dto.CdOrderCheckoutResponse;
import com.vocalharmonys.backend.dto.CdOrderRequest;
import com.vocalharmonys.backend.dto.CdOrderResponse;
import com.vocalharmonys.backend.dto.CdOrderSummaryResponse;
import com.vocalharmonys.backend.service.CdOrderService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The "Commander" dialog on the Boutique page. Starting a checkout session
 * and reading back an order summary by Stripe session id are both public;
 * listing every order requires a logged-in member — see SecurityConfig.
 * Actual payment confirmation happens out-of-band via StripeWebhookController.
 */
@RestController
@RequestMapping("/api/cd-orders")
public class CdOrderController {

    private final CdOrderService cdOrderService;

    public CdOrderController(CdOrderService cdOrderService) {
        this.cdOrderService = cdOrderService;
    }

    @GetMapping
    public List<CdOrderResponse> listAll() {
        return cdOrderService.listAll();
    }

    @PostMapping("/checkout-sessions")
    @ResponseStatus(HttpStatus.CREATED)
    public CdOrderCheckoutResponse createCheckoutSession(@Valid @RequestBody CdOrderRequest request) {
        return cdOrderService.createCheckoutSession(request);
    }

    @GetMapping("/by-session/{stripeCheckoutSessionId}")
    public CdOrderSummaryResponse getSummaryBySession(@PathVariable String stripeCheckoutSessionId) {
        return cdOrderService.findSummaryByStripeSessionId(stripeCheckoutSessionId);
    }
}
