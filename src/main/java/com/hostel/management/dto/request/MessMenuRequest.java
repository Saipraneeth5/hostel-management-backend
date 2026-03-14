package com.hostel.management.dto.request;

import jakarta.validation.constraints.NotBlank;

public class MessMenuRequest {

    @NotBlank(message = "Day of week is required")
    private String dayOfWeek;

    @NotBlank(message = "Meal type is required")
    private String mealType;

    @NotBlank(message = "Items are required")
    private String items;

    private String  description;
    private boolean isSpecial = false;

    public MessMenuRequest() {}

    public String getDayOfWeek()         { return dayOfWeek; }
    public void setDayOfWeek(String v)   { this.dayOfWeek = v; }
    public String getMealType()          { return mealType; }
    public void setMealType(String v)    { this.mealType = v; }
    public String getItems()             { return items; }
    public void setItems(String v)       { this.items = v; }
    public String getDescription()       { return description; }
    public void setDescription(String v) { this.description = v; }
    public boolean isSpecial()           { return isSpecial; }
    public void setSpecial(boolean v)    { this.isSpecial = v; }
}
