package com.hostel.management.dto.response;

import java.time.LocalDateTime;

/**
 * Complaint response DTO.
 * student field uses StudentSummaryDto — no complaints list inside,
 * which breaks Complaint → Student → complaints → Complaint cycle.
 */
public class ComplaintDto {

    private Long               id;
    private StudentSummaryDto  student;
    private String             title;
    private String             description;
    private String             category;
    private String             status;
    private String             priority;
    private String             adminRemarks;
    private LocalDateTime      resolvedAt;
    private LocalDateTime      createdAt;
    private LocalDateTime      updatedAt;

    public ComplaintDto() {}

    public Long getId()                       { return id; }
    public void setId(Long id)                { this.id = id; }
    public StudentSummaryDto getStudent()     { return student; }
    public void setStudent(StudentSummaryDto v){ this.student = v; }
    public String getTitle()                  { return title; }
    public void setTitle(String v)            { this.title = v; }
    public String getDescription()            { return description; }
    public void setDescription(String v)      { this.description = v; }
    public String getCategory()               { return category; }
    public void setCategory(String v)         { this.category = v; }
    public String getStatus()                 { return status; }
    public void setStatus(String v)           { this.status = v; }
    public String getPriority()               { return priority; }
    public void setPriority(String v)         { this.priority = v; }
    public String getAdminRemarks()           { return adminRemarks; }
    public void setAdminRemarks(String v)     { this.adminRemarks = v; }
    public LocalDateTime getResolvedAt()      { return resolvedAt; }
    public void setResolvedAt(LocalDateTime v){ this.resolvedAt = v; }
    public LocalDateTime getCreatedAt()       { return createdAt; }
    public void setCreatedAt(LocalDateTime v) { this.createdAt = v; }
    public LocalDateTime getUpdatedAt()       { return updatedAt; }
    public void setUpdatedAt(LocalDateTime v) { this.updatedAt = v; }
}
