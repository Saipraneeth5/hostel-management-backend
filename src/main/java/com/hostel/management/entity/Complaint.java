package com.hostel.management.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "complaints")
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ComplaintCategory category;

    @Enumerated(EnumType.STRING)
    private ComplaintStatus status = ComplaintStatus.PENDING;

    @Enumerated(EnumType.STRING)
    private Priority priority = Priority.MEDIUM;

    @Column(name = "admin_remarks", columnDefinition = "TEXT")
    private String adminRemarks;

    @Column(name = "resolved_at")
    private LocalDateTime resolvedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public enum ComplaintCategory { MAINTENANCE, CLEANLINESS, FOOD, ELECTRICITY, WATER, INTERNET, SECURITY, OTHER }
    public enum ComplaintStatus { PENDING, IN_PROGRESS, RESOLVED, REJECTED }
    public enum Priority { LOW, MEDIUM, HIGH, URGENT }

    public Complaint() {}

    @PrePersist
    protected void onCreate() { createdAt = LocalDateTime.now(); updatedAt = LocalDateTime.now(); }

    @PreUpdate
    protected void onUpdate() { updatedAt = LocalDateTime.now(); }

    // ── Builder ──────────────────────────────────────────────────────────────
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private Student student;
        private String title, description, adminRemarks;
        private ComplaintCategory category;
        private ComplaintStatus status = ComplaintStatus.PENDING;
        private Priority priority = Priority.MEDIUM;
        private LocalDateTime resolvedAt;

        public Builder id(Long v) { this.id = v; return this; }
        public Builder student(Student v) { this.student = v; return this; }
        public Builder title(String v) { this.title = v; return this; }
        public Builder description(String v) { this.description = v; return this; }
        public Builder category(ComplaintCategory v) { this.category = v; return this; }
        public Builder status(ComplaintStatus v) { this.status = v; return this; }
        public Builder priority(Priority v) { this.priority = v; return this; }
        public Builder adminRemarks(String v) { this.adminRemarks = v; return this; }
        public Builder resolvedAt(LocalDateTime v) { this.resolvedAt = v; return this; }

        public Complaint build() {
            Complaint c = new Complaint();
            c.id = id; c.student = student; c.title = title; c.description = description;
            c.category = category; c.status = status; c.priority = priority;
            c.adminRemarks = adminRemarks; c.resolvedAt = resolvedAt;
            return c;
        }
    }

    // ── Getters & Setters ────────────────────────────────────────────────────
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public ComplaintCategory getCategory() { return category; }
    public void setCategory(ComplaintCategory category) { this.category = category; }
    public ComplaintStatus getStatus() { return status; }
    public void setStatus(ComplaintStatus status) { this.status = status; }
    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }
    public String getAdminRemarks() { return adminRemarks; }
    public void setAdminRemarks(String adminRemarks) { this.adminRemarks = adminRemarks; }
    public LocalDateTime getResolvedAt() { return resolvedAt; }
    public void setResolvedAt(LocalDateTime resolvedAt) { this.resolvedAt = resolvedAt; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
