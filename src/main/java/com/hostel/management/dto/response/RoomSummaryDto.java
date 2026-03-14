package com.hostel.management.dto.response;

import java.math.BigDecimal;

/**
 * Slim room projection embedded inside StudentDto.
 * Does NOT contain a students list — that would recreate the circular reference.
 */
public class RoomSummaryDto {

    private Long       id;
    private String     roomNumber;
    private String     roomType;
    private Integer    capacity;
    private Integer    occupiedCount;
    private Integer    floor;
    private String     block;
    private BigDecimal monthlyFee;
    private String     status;
    private boolean    hasAc;
    private boolean    hasAttachedBathroom;

    public RoomSummaryDto() {}

    public Long getId()                          { return id; }
    public void setId(Long id)                   { this.id = id; }
    public String getRoomNumber()                { return roomNumber; }
    public void setRoomNumber(String v)          { this.roomNumber = v; }
    public String getRoomType()                  { return roomType; }
    public void setRoomType(String v)            { this.roomType = v; }
    public Integer getCapacity()                 { return capacity; }
    public void setCapacity(Integer v)           { this.capacity = v; }
    public Integer getOccupiedCount()            { return occupiedCount; }
    public void setOccupiedCount(Integer v)      { this.occupiedCount = v; }
    public Integer getFloor()                    { return floor; }
    public void setFloor(Integer v)              { this.floor = v; }
    public String getBlock()                     { return block; }
    public void setBlock(String v)               { this.block = v; }
    public BigDecimal getMonthlyFee()            { return monthlyFee; }
    public void setMonthlyFee(BigDecimal v)      { this.monthlyFee = v; }
    public String getStatus()                    { return status; }
    public void setStatus(String v)              { this.status = v; }
    public boolean isHasAc()                     { return hasAc; }
    public void setHasAc(boolean v)              { this.hasAc = v; }
    public boolean isHasAttachedBathroom()       { return hasAttachedBathroom; }
    public void setHasAttachedBathroom(boolean v){ this.hasAttachedBathroom = v; }
}
