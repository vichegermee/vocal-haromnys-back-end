package com.vocalharmonys.backend.entity;

import java.math.BigDecimal;

/** The two delivery methods offered when ordering a CD, each with a fixed price. */
public enum ShippingOption {
    STANDARD("Livraison standard (5 jours)", new BigDecimal("2.99")),
    EXPRESS("Livraison express", new BigDecimal("5.00"));

    private final String label;
    private final BigDecimal cost;

    ShippingOption(String label, BigDecimal cost) {
        this.label = label;
        this.cost = cost;
    }

    public String getLabel() {
        return label;
    }

    public BigDecimal getCost() {
        return cost;
    }
}
