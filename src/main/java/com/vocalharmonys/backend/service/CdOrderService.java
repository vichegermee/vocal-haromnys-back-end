package com.vocalharmonys.backend.service;

import com.vocalharmonys.backend.dto.CdOrderRequest;
import com.vocalharmonys.backend.dto.CdOrderResponse;
import com.vocalharmonys.backend.entity.Cd;
import com.vocalharmonys.backend.entity.CdOrder;
import com.vocalharmonys.backend.repository.CdOrderRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/** Backs the "Commander" dialog on the Boutique page. */
@Service
public class CdOrderService {

    private final CdOrderRepository cdOrderRepository;
    private final CdService cdService;

    public CdOrderService(CdOrderRepository cdOrderRepository, CdService cdService) {
        this.cdOrderRepository = cdOrderRepository;
        this.cdService = cdService;
    }

    public List<CdOrderResponse> listAll() {
        return cdOrderRepository.findAllByOrderByCreatedAtDesc().stream().map(CdOrderResponse::from).toList();
    }

    public CdOrderResponse create(CdOrderRequest request) {
        Cd cd = cdService.findOrThrow(request.cdId());

        CdOrder order = new CdOrder();
        order.setCd(cd);
        // Snapshot now, so a later price/title edit on the CD never rewrites
        // what this customer actually agreed to order.
        order.setCdTitleSnapshot(cd.getTitle());
        order.setUnitPriceSnapshot(cd.getPrice());
        order.setCustomerName(request.customerName());
        order.setCustomerEmail(request.customerEmail());
        order.setQuantity(request.quantity());
        order.setMessage(request.message());

        return CdOrderResponse.from(cdOrderRepository.save(order));
    }
}
