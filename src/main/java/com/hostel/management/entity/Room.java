package com.hostel.management.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_number", unique = true, nullable = false)
    private String roomNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type", nullable = false)
    private RoomType roomType;

    @Column(nullable = false)
    private Integer capacity;

    @Column(name = "occupied_count")
    private Integer occupiedCount = 0;

    private Integer floor;
    private String block;

    @Column(name = "monthly_fee", precision = 10, scale = 2)
    private BigDecimal monthlyFee;

    @Enumerated(EnumType.STRING)
    private RoomStatus status = RoomStatus.AVAILABLE;

    private String description;

    @Column(name = "has_ac")
    private boolean hasAc = false;

    @Column(name = "has_attached_bathroom")
    private boolean hasAttachedBathroom = false;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<Student> students;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public enum RoomType { SINGLE, DOUBLE, TRIPLE, DORMITORY }
    public enum RoomStatus { AVAILABLE, FULL, MAINTENANCE, RESERVED }

    public Room() {}

    @PrePersist
    protected void onCreate() { createdAt = LocalDateTime.now(); updatedAt = LocalDateTime.now(); }

    @PreUpdate
    protected void onUpdate() { updatedAt = LocalDateTime.now(); }

    public boolean hasVacancy() { return occupiedCount < capacity; }
    public int getAvailableSlots() { return capacity - occupiedCount; }

    // ── Builder ──────────────────────────────────────────────────────────────
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String roomNumber, block, description;
        private RoomType roomType;
        private Integer capacity, floor;
        private Integer occupiedCount = 0;
        private BigDecimal monthlyFee;
        private RoomStatus status = RoomStatus.AVAILABLE;
        private boolean hasAc = false, hasAttachedBathroom = false;

        public Builder id(Long v) { this.id = v; return this; }
        public Builder roomNumber(String v) { this.roomNumber = v; return this; }
        public Builder roomType(RoomType v) { this.roomType = v; return this; }
        public Builder capacity(Integer v) { this.capacity = v; return this; }
        public Builder occupiedCount(Integer v) { this.occupiedCount = v; return this; }
        public Builder floor(Integer v) { this.floor = v; return this; }
        public Builder block(String v) { this.block = v; return this; }
        public Builder monthlyFee(BigDecimal v) { this.monthlyFee = v; return this; }
        public Builder status(RoomStatus v) { this.status = v; return this; }
        public Builder description(String v) { this.description = v; return this; }
        public Builder hasAc(boolean v) { this.hasAc = v; return this; }
        public Builder hasAttachedBathroom(boolean v) { this.hasAttachedBathroom = v; return this; }

        public Room build() {
            Room r = new Room();
            r.id = id; r.roomNumber = roomNumber; r.roomType = roomType; r.capacity = capacity;
            r.occupiedCount = occupiedCount; r.floor = floor; r.block = block;
            r.monthlyFee = monthlyFee; r.status = status; r.description = description;
            r.hasAc = hasAc; r.hasAttachedBathroom = hasAttachedBathroom;
            return r;
        }
    }

    // ── Getters & Setters ────────────────────────────────────────────────────
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    public RoomType getRoomType() { return roomType; }
    public void setRoomType(RoomType roomType) { this.roomType = roomType; }
    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
    public Integer getOccupiedCount() { return occupiedCount; }
    public void setOccupiedCount(Integer occupiedCount) { this.occupiedCount = occupiedCount; }
    public Integer getFloor() { return floor; }
    public void setFloor(Integer floor) { this.floor = floor; }
    public String getBlock() { return block; }
    public void setBlock(String block) { this.block = block; }
    public BigDecimal getMonthlyFee() { return monthlyFee; }
    public void setMonthlyFee(BigDecimal monthlyFee) { this.monthlyFee = monthlyFee; }
    public RoomStatus getStatus() { return status; }
    public void setStatus(RoomStatus status) { this.status = status; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isHasAc() { return hasAc; }
    public void setHasAc(boolean hasAc) { this.hasAc = hasAc; }
    public boolean isHasAttachedBathroom() { return hasAttachedBathroom; }
    public void setHasAttachedBathroom(boolean hasAttachedBathroom) { this.hasAttachedBathroom = hasAttachedBathroom; }
    public List<Student> getStudents() { return students; }
    public void setStudents(List<Student> students) { this.students = students; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
