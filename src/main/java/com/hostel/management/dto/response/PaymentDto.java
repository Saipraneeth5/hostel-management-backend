package com.hostel.management.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Payment response DTO.
 * student field uses StudentSummaryDto to avoid deep recursion.
 */
public class PaymentDto {

    private Long              id;
    private StudentSummaryDto student;
    private String            transactionId;
    private BigDecimal        amount;
    private String            paymentType;
    private String            status;
    private String            paymentMethod;
    private LocalDate         paymentDate;
    private LocalDate         dueDate;
    private String            forMonth;
    private String            description;
    private String            receipt;
    private LocalDateTime     createdAt;
    private LocalDateTime     updatedAt;

    public PaymentDto() {}

    public Long getId()                        { return id; }
    public void setId(Long id)                 { this.id = id; }
    public StudentSummaryDto getStudent()      { return student; }
    public void setStudent(StudentSummaryDto v){ this.student = v; }
    public String getTransactionId()           { return transactionId; }
    public void setTransactionId(String v)     { this.transactionId = v; }
    public BigDecimal getAmount()              { return amount; }
    public void setAmount(BigDecimal v)        { this.amount = v; }
    public String getPaymentType()             { return paymentType; }
    public void setPaymentType(String v)       { this.paymentType = v; }
    public String getStatus()                  { return status; }
    public void setStatus(String v)            { this.status = v; }
    public String getPaymentMethod()           { return paymentMethod; }
    public void setPaymentMethod(String v)     { this.paymentMethod = v; }
    public LocalDate getPaymentDate()          { return paymentDate; }
    public void setPaymentDate(LocalDate v)    { this.paymentDate = v; }
    public LocalDate getDueDate()              { return dueDate; }
    public void setDueDate(LocalDate v)        { this.dueDate = v; }
    public String getForMonth()                { return forMonth; }
    public void setForMonth(String v)          { this.forMonth = v; }
    public String getDescription()             { return description; }
    public void setDescription(String v)       { this.description = v; }
    public String getReceipt()                 { return receipt; }
    public void setReceipt(String v)           { this.receipt = v; }
    public LocalDateTime getCreatedAt()        { return createdAt; }
    public void setCreatedAt(LocalDateTime v)  { this.createdAt = v; }
    public LocalDateTime getUpdatedAt()        { return updatedAt; }
    public void setUpdatedAt(LocalDateTime v)  { this.updatedAt = v; }
}
