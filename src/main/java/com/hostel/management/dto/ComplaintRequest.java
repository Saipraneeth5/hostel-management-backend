package com.hostel.management.dto;

import com.hostel.management.entity.Complaint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ComplaintRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Category is required")
    private Complaint.ComplaintCategory category;

    private Complaint.Priority priority = Complaint.Priority.MEDIUM;

    public ComplaintRequest() {}

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Complaint.ComplaintCategory getCategory() { return category; }
    public void setCategory(Complaint.ComplaintCategory category) { this.category = category; }
    public Complaint.Priority getPriority() { return priority; }
    public void setPriority(Complaint.Priority priority) { this.priority = priority; }
}
