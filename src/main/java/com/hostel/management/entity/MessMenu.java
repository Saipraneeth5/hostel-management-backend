package com.hostel.management.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "mess_menu")
public class MessMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "day_of_week", nullable = false)
    private String dayOfWeek;

    @Enumerated(EnumType.STRING)
    @Column(name = "meal_type", nullable = false)
    private MealType mealType;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String items;

    private String description;

    @Column(name = "is_special")
    private boolean isSpecial = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public enum MealType { BREAKFAST, LUNCH, SNACKS, DINNER }

    public MessMenu() {}

    @PrePersist
    protected void onCreate() { createdAt = LocalDateTime.now(); updatedAt = LocalDateTime.now(); }

    @PreUpdate
    protected void onUpdate() { updatedAt = LocalDateTime.now(); }

    // ── Builder ──────────────────────────────────────────────────────────────
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String dayOfWeek, items, description;
        private MealType mealType;
        private boolean isSpecial = false;

        public Builder id(Long v) { this.id = v; return this; }
        public Builder dayOfWeek(String v) { this.dayOfWeek = v; return this; }
        public Builder mealType(MealType v) { this.mealType = v; return this; }
        public Builder items(String v) { this.items = v; return this; }
        public Builder description(String v) { this.description = v; return this; }
        public Builder isSpecial(boolean v) { this.isSpecial = v; return this; }

        public MessMenu build() {
            MessMenu m = new MessMenu();
            m.id = id; m.dayOfWeek = dayOfWeek; m.mealType = mealType;
            m.items = items; m.description = description; m.isSpecial = isSpecial;
            return m;
        }
    }

    // ── Getters & Setters ────────────────────────────────────────────────────
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(String dayOfWeek) { this.dayOfWeek = dayOfWeek; }
    public MealType getMealType() { return mealType; }
    public void setMealType(MealType mealType) { this.mealType = mealType; }
    public String getItems() { return items; }
    public void setItems(String items) { this.items = items; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isSpecial() { return isSpecial; }
    public void setSpecial(boolean special) { isSpecial = special; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
