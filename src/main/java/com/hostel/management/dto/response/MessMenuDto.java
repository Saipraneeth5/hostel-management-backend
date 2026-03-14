package com.hostel.management.dto.response;

import java.time.LocalDateTime;

public class MessMenuDto {

    private Long          id;
    private String        dayOfWeek;
    private String        mealType;
    private String        items;
    private String        description;
    private boolean       isSpecial;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public MessMenuDto() {}

    public Long getId()                       { return id; }
    public void setId(Long id)                { this.id = id; }
    public String getDayOfWeek()              { return dayOfWeek; }
    public void setDayOfWeek(String v)        { this.dayOfWeek = v; }
    public String getMealType()               { return mealType; }
    public void setMealType(String v)         { this.mealType = v; }
    public String getItems()                  { return items; }
    public void setItems(String v)            { this.items = v; }
    public String getDescription()            { return description; }
    public void setDescription(String v)      { this.description = v; }
    public boolean isSpecial()                { return isSpecial; }
    public void setSpecial(boolean v)         { this.isSpecial = v; }
    public LocalDateTime getCreatedAt()       { return createdAt; }
    public void setCreatedAt(LocalDateTime v) { this.createdAt = v; }
    public LocalDateTime getUpdatedAt()       { return updatedAt; }
    public void setUpdatedAt(LocalDateTime v) { this.updatedAt = v; }
}
