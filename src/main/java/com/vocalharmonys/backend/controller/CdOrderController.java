package com.vocalharmonys.backend.controller;

import com.vocalharmonys.backend.dto.CdOrderRequest;
import com.vocalharmonys.backend.dto.CdOrderResponse;
import com.vocalharmonys.backend.service.CdOrderService;
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
 * The "Commander" dialog on the Boutique page. Creating an order is public
 * (any visitor can order a CD); listing orders back requires a logged-in
 * member — see SecurityConfig.
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CdOrderResponse create(@Valid @RequestBody CdOrderRequest request) {
        return cdOrderService.create(request);
    }
}
