package com.hostel.management.dto.response;

import java.time.LocalDateTime;

/**
 * Notification response DTO.
 * Only userId/username are carried — not the full User graph.
 */
public class NotificationDto {

    private Long          id;
    private String        title;
    private String        message;
    private String        notificationType;
    private Long          userId;
    private String        username;
    private boolean       isRead;
    private boolean       isGlobal;
    private String        sentBy;
    private LocalDateTime createdAt;
    private LocalDateTime readAt;

    public NotificationDto() {}

    public Long getId()                       { return id; }
    public void setId(Long id)                { this.id = id; }
    public String getTitle()                  { return title; }
    public void setTitle(String v)            { this.title = v; }
    public String getMessage()                { return message; }
    public void setMessage(String v)          { this.message = v; }
    public String getNotificationType()       { return notificationType; }
    public void setNotificationType(String v) { this.notificationType = v; }
    public Long getUserId()                   { return userId; }
    public void setUserId(Long v)             { this.userId = v; }
    public String getUsername()               { return username; }
    public void setUsername(String v)         { this.username = v; }
    public boolean isRead()                   { return isRead; }
    public void setRead(boolean v)            { this.isRead = v; }
    public boolean isGlobal()                 { return isGlobal; }
    public void setGlobal(boolean v)          { this.isGlobal = v; }
    public String getSentBy()                 { return sentBy; }
    public void setSentBy(String v)           { this.sentBy = v; }
    public LocalDateTime getCreatedAt()       { return createdAt; }
    public void setCreatedAt(LocalDateTime v) { this.createdAt = v; }
    public LocalDateTime getReadAt()          { return readAt; }
    public void setReadAt(LocalDateTime v)    { this.readAt = v; }
}
