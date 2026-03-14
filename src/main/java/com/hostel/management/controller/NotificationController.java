package com.hostel.management.controller;

import com.hostel.management.dto.response.NotificationDto;
import com.hostel.management.entity.Notification;
import com.hostel.management.entity.User;
import com.hostel.management.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public ResponseEntity<List<NotificationDto>> getMyNotifications(
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(notificationService.getNotificationsForUser(user.getId()));
    }

    @GetMapping("/unread")
    public ResponseEntity<List<NotificationDto>> getUnreadNotifications(
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(notificationService.getUnreadNotifications(user.getId()));
    }

    @GetMapping("/unread/count")
    public ResponseEntity<Map<String, Long>> countUnread(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(Map.of("unreadCount",
                notificationService.countUnread(user.getId())));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<NotificationDto>> getAllNotifications() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    @PostMapping("/send/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    public ResponseEntity<NotificationDto> sendToUser(
            @PathVariable Long userId,
            @RequestParam String title,
            @RequestParam String message,
            @RequestParam(defaultValue = "GENERAL") Notification.NotificationType type,
            @AuthenticationPrincipal User sender) {
        return ResponseEntity.ok(
                notificationService.sendToUser(userId, title, message, type,
                        sender.getUsername()));
    }

    @PostMapping("/global")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    public ResponseEntity<NotificationDto> sendGlobal(
            @RequestParam String title,
            @RequestParam String message,
            @RequestParam(defaultValue = "GENERAL") Notification.NotificationType type,
            @AuthenticationPrincipal User sender) {
        return ResponseEntity.ok(
                notificationService.sendGlobalNotification(title, message, type,
                        sender.getUsername()));
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<NotificationDto> markAsRead(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.markAsRead(id));
    }

    @PutMapping("/read-all")
    public ResponseEntity<Map<String, String>> markAllAsRead(
            @AuthenticationPrincipal User user) {
        notificationService.markAllAsRead(user.getId());
        return ResponseEntity.ok(Map.of("message", "All notifications marked as read"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.ok(Map.of("message", "Notification deleted"));
    }
}
