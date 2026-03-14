package com.hostel.management.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_type")
    private NotificationType notificationType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "is_read")
    private boolean isRead = false;

    @Column(name = "is_global")
    private boolean isGlobal = false;

    @Column(name = "sent_by")
    private String sentBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "read_at")
    private LocalDateTime readAt;

    public enum NotificationType { PAYMENT_DUE, COMPLAINT_UPDATE, GENERAL, MAINTENANCE, EMERGENCY, MESS_UPDATE }

    public Notification() {}

    @PrePersist
    protected void onCreate() { createdAt = LocalDateTime.now(); }

    // ── Builder ──────────────────────────────────────────────────────────────
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String title, message, sentBy;
        private NotificationType notificationType;
        private User user;
        private boolean isRead = false, isGlobal = false;

        public Builder id(Long v) { this.id = v; return this; }
        public Builder title(String v) { this.title = v; return this; }
        public Builder message(String v) { this.message = v; return this; }
        public Builder notificationType(NotificationType v) { this.notificationType = v; return this; }
        public Builder user(User v) { this.user = v; return this; }
        public Builder isRead(boolean v) { this.isRead = v; return this; }
        public Builder isGlobal(boolean v) { this.isGlobal = v; return this; }
        public Builder sentBy(String v) { this.sentBy = v; return this; }

        public Notification build() {
            Notification n = new Notification();
            n.id = id; n.title = title; n.message = message;
            n.notificationType = notificationType; n.user = user;
            n.isRead = isRead; n.isGlobal = isGlobal; n.sentBy = sentBy;
            return n;
        }
    }

    // ── Getters & Setters ────────────────────────────────────────────────────
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public NotificationType getNotificationType() { return notificationType; }
    public void setNotificationType(NotificationType notificationType) { this.notificationType = notificationType; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { isRead = read; }
    public boolean isGlobal() { return isGlobal; }
    public void setGlobal(boolean global) { isGlobal = global; }
    public String getSentBy() { return sentBy; }
    public void setSentBy(String sentBy) { this.sentBy = sentBy; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getReadAt() { return readAt; }
    public void setReadAt(LocalDateTime readAt) { this.readAt = readAt; }
}
