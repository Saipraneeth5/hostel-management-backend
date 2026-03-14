package com.hostel.management.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Replaces accepting a raw Payment entity in POST endpoints.
 * Student is resolved from the path variable — not accepted from body.
 */
public class PaymentRequest {

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    @NotBlank(message = "Payment type is required")
    private String paymentType;

    private String    paymentMethod;
    private LocalDate dueDate;
    private String    forMonth;
    private String    description;

    public PaymentRequest() {}

    public BigDecimal getAmount()            { return amount; }
    public void setAmount(BigDecimal v)      { this.amount = v; }
    public String getPaymentType()           { return paymentType; }
    public void setPaymentType(String v)     { this.paymentType = v; }
    public String getPaymentMethod()         { return paymentMethod; }
    public void setPaymentMethod(String v)   { this.paymentMethod = v; }
    public LocalDate getDueDate()            { return dueDate; }
    public void setDueDate(LocalDate v)      { this.dueDate = v; }
    public String getForMonth()              { return forMonth; }
    public void setForMonth(String v)        { this.forMonth = v; }
    public String getDescription()           { return description; }
    public void setDescription(String v)     { this.description = v; }
}
