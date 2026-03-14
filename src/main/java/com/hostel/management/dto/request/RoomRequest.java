package com.hostel.management.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

/**
 * Replaces accepting a raw Room entity in POST/PUT endpoints.
 * Prevents clients from injecting occupiedCount or students.
 */
public class RoomRequest {

    @NotBlank(message = "Room number is required")
    private String roomNumber;

    @NotBlank(message = "Room type is required")
    private String roomType;

    @NotNull(message = "Capacity is required")
    @Positive(message = "Capacity must be positive")
    private Integer capacity;

    private Integer    floor;
    private String     block;
    private BigDecimal monthlyFee;
    private String     status;
    private String     description;
    private boolean    hasAc               = false;
    private boolean    hasAttachedBathroom = false;

    public RoomRequest() {}

    public String getRoomNumber()                { return roomNumber; }
    public void setRoomNumber(String v)          { this.roomNumber = v; }
    public String getRoomType()                  { return roomType; }
    public void setRoomType(String v)            { this.roomType = v; }
    public Integer getCapacity()                 { return capacity; }
    public void setCapacity(Integer v)           { this.capacity = v; }
    public Integer getFloor()                    { return floor; }
    public void setFloor(Integer v)              { this.floor = v; }
    public String getBlock()                     { return block; }
    public void setBlock(String v)               { this.block = v; }
    public BigDecimal getMonthlyFee()            { return monthlyFee; }
    public void setMonthlyFee(BigDecimal v)      { this.monthlyFee = v; }
    public String getStatus()                    { return status; }
    public void setStatus(String v)              { this.status = v; }
    public String getDescription()               { return description; }
    public void setDescription(String v)         { this.description = v; }
    public boolean isHasAc()                     { return hasAc; }
    public void setHasAc(boolean v)              { this.hasAc = v; }
    public boolean isHasAttachedBathroom()       { return hasAttachedBathroom; }
    public void setHasAttachedBathroom(boolean v){ this.hasAttachedBathroom = v; }
}
