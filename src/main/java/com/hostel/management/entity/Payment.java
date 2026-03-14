package com.hostel.management.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(name = "transaction_id", unique = true)
    private String transactionId;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type", nullable = false)
    private PaymentType paymentType;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status = PaymentStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "for_month")
    private String forMonth;

    private String description;
    private String receipt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public enum PaymentType { ROOM_RENT, MESS_FEE, SECURITY_DEPOSIT, FINE, OTHER }
    public enum PaymentStatus { PENDING, PAID, OVERDUE, WAIVED, FAILED }
    public enum PaymentMethod { CASH, ONLINE, UPI, BANK_TRANSFER, CHEQUE }

    public Payment() {}

    @PrePersist
    protected void onCreate() { createdAt = LocalDateTime.now(); updatedAt = LocalDateTime.now(); }

    @PreUpdate
    protected void onUpdate() { updatedAt = LocalDateTime.now(); }

    // ── Builder ──────────────────────────────────────────────────────────────
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private Student student;
        private String transactionId, forMonth, description, receipt;
        private BigDecimal amount;
        private PaymentType paymentType;
        private PaymentStatus status = PaymentStatus.PENDING;
        private PaymentMethod paymentMethod;
        private LocalDate paymentDate, dueDate;

        public Builder id(Long v) { this.id = v; return this; }
        public Builder student(Student v) { this.student = v; return this; }
        public Builder transactionId(String v) { this.transactionId = v; return this; }
        public Builder amount(BigDecimal v) { this.amount = v; return this; }
        public Builder paymentType(PaymentType v) { this.paymentType = v; return this; }
        public Builder status(PaymentStatus v) { this.status = v; return this; }
        public Builder paymentMethod(PaymentMethod v) { this.paymentMethod = v; return this; }
        public Builder paymentDate(LocalDate v) { this.paymentDate = v; return this; }
        public Builder dueDate(LocalDate v) { this.dueDate = v; return this; }
        public Builder forMonth(String v) { this.forMonth = v; return this; }
        public Builder description(String v) { this.description = v; return this; }
        public Builder receipt(String v) { this.receipt = v; return this; }

        public Payment build() {
            Payment p = new Payment();
            p.id = id; p.student = student; p.transactionId = transactionId; p.amount = amount;
            p.paymentType = paymentType; p.status = status; p.paymentMethod = paymentMethod;
            p.paymentDate = paymentDate; p.dueDate = dueDate; p.forMonth = forMonth;
            p.description = description; p.receipt = receipt;
            return p;
        }
    }

    // ── Getters & Setters ────────────────────────────────────────────────────
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public PaymentType getPaymentType() { return paymentType; }
    public void setPaymentType(PaymentType paymentType) { this.paymentType = paymentType; }
    public PaymentStatus getStatus() { return status; }
    public void setStatus(PaymentStatus status) { this.status = status; }
    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }
    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public String getForMonth() { return forMonth; }
    public void setForMonth(String forMonth) { this.forMonth = forMonth; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getReceipt() { return receipt; }
    public void setReceipt(String receipt) { this.receipt = receipt; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
