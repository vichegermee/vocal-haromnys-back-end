package com.vocalharmonys.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * A "Commander" request from the Boutique page. {@link #cdTitleSnapshot} and
 * {@link #unitPriceSnapshot} are copied from the {@link Cd} at order time so
 * this record stays accurate even if the CD's title or price changes later;
 * {@link #shippingCost} and {@link #totalAmount} are likewise snapshotted
 * from the chosen {@link ShippingOption} at order time.
 */
@Entity
@Table(name = "cd_orders")
public class CdOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cd_id")
    private Cd cd;

    @Column(name = "cd_title_snapshot", nullable = false, length = 200)
    private String cdTitleSnapshot;

    @Column(name = "unit_price_snapshot", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPriceSnapshot;

    @Column(name = "customer_name", nullable = false, length = 150)
    private String customerName;

    @Column(name = "customer_email", nullable = false, length = 150)
    private String customerEmail;

    @Column(name = "customer_phone", nullable = false, length = 30)
    private String customerPhone;

    @Column(nullable = false)
    private int quantity = 1;

    @Column(name = "shipping_street", nullable = false, length = 255)
    private String shippingStreet;

    @Column(name = "shipping_postal_code", nullable = false, length = 20)
    private String shippingPostalCode;

    @Column(name = "shipping_city", nullable = false, length = 150)
    private String shippingCity;

    @Column(name = "shipping_country", nullable = false, length = 100)
    private String shippingCountry;

    @Enumerated(EnumType.STRING)
    @Column(name = "shipping_option", nullable = false, length = 20)
    private ShippingOption shippingOption;

    @Column(name = "shipping_cost", nullable = false, precision = 10, scale = 2)
    private BigDecimal shippingCost;

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(columnDefinition = "text")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RequestStatus status = RequestStatus.RECEIVED;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false, length = 20)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    @Column(name = "stripe_checkout_session_id", length = 255)
    private String stripeCheckoutSessionId;

    @Column(name = "stripe_payment_intent_id", length = 255)
    private String stripePaymentIntentId;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public CdOrder() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cd getCd() {
        return cd;
    }

    public void setCd(Cd cd) {
        this.cd = cd;
    }

    public String getCdTitleSnapshot() {
        return cdTitleSnapshot;
    }

    public void setCdTitleSnapshot(String cdTitleSnapshot) {
        this.cdTitleSnapshot = cdTitleSnapshot;
    }

    public BigDecimal getUnitPriceSnapshot() {
        return unitPriceSnapshot;
    }

    public void setUnitPriceSnapshot(BigDecimal unitPriceSnapshot) {
        this.unitPriceSnapshot = unitPriceSnapshot;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getShippingStreet() {
        return shippingStreet;
    }

    public void setShippingStreet(String shippingStreet) {
        this.shippingStreet = shippingStreet;
    }

    public String getShippingPostalCode() {
        return shippingPostalCode;
    }

    public void setShippingPostalCode(String shippingPostalCode) {
        this.shippingPostalCode = shippingPostalCode;
    }

    public String getShippingCity() {
        return shippingCity;
    }

    public void setShippingCity(String shippingCity) {
        this.shippingCity = shippingCity;
    }

    public String getShippingCountry() {
        return shippingCountry;
    }

    public void setShippingCountry(String shippingCountry) {
        this.shippingCountry = shippingCountry;
    }

    public ShippingOption getShippingOption() {
        return shippingOption;
    }

    public void setShippingOption(ShippingOption shippingOption) {
        this.shippingOption = shippingOption;
    }

    public BigDecimal getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(BigDecimal shippingCost) {
        this.shippingCost = shippingCost;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getStripeCheckoutSessionId() {
        return stripeCheckoutSessionId;
    }

    public void setStripeCheckoutSessionId(String stripeCheckoutSessionId) {
        this.stripeCheckoutSessionId = stripeCheckoutSessionId;
    }

    public String getStripePaymentIntentId() {
        return stripePaymentIntentId;
    }

    public void setStripePaymentIntentId(String stripePaymentIntentId) {
        this.stripePaymentIntentId = stripePaymentIntentId;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
