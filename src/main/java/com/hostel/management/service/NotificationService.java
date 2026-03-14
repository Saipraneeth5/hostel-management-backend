package com.hostel.management.service;

import com.hostel.management.dto.response.NotificationDto;
import com.hostel.management.entity.Notification;
import com.hostel.management.entity.User;
import com.hostel.management.exception.GlobalExceptionHandler.ResourceNotFoundException;
import com.hostel.management.mapper.EntityMapper;
import com.hostel.management.repository.NotificationRepository;
import com.hostel.management.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository         userRepository;
    private final EntityMapper           mapper;

    public NotificationService(NotificationRepository notificationRepository,
                               UserRepository userRepository,
                               EntityMapper mapper) {
        this.notificationRepository = notificationRepository;
        this.userRepository         = userRepository;
        this.mapper                 = mapper;
    }

    @Transactional(readOnly = true)
    public List<NotificationDto> getNotificationsForUser(Long userId) {
        return mapper.toNotificationDtoList(notificationRepository.findAllForUser(userId));
    }

    @Transactional(readOnly = true)
    public List<NotificationDto> getUnreadNotifications(Long userId) {
        return mapper.toNotificationDtoList(
                notificationRepository.findByUserIdAndIsRead(userId, false));
    }

    @Transactional(readOnly = true)
    public long countUnread(Long userId) {
        return notificationRepository.countUnreadForUser(userId);
    }

    @Transactional(readOnly = true)
    public List<NotificationDto> getAllNotifications() {
        return mapper.toNotificationDtoList(notificationRepository.findAllWithUser());
    }

    @Transactional
    public NotificationDto sendToUser(Long userId, String title, String message,
                                      Notification.NotificationType type, String sentBy) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with id: " + userId));
        Notification notification = Notification.builder()
                .user(user).title(title).message(message)
                .notificationType(type).isGlobal(false).sentBy(sentBy)
                .build();
        return mapper.toNotificationDto(notificationRepository.save(notification));
    }

    @Transactional
    public NotificationDto sendGlobalNotification(String title, String message,
                                                   Notification.NotificationType type,
                                                   String sentBy) {
        Notification notification = Notification.builder()
                .title(title).message(message)
                .notificationType(type).isGlobal(true).sentBy(sentBy)
                .build();
        return mapper.toNotificationDto(notificationRepository.save(notification));
    }

    @Transactional
    public NotificationDto markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Notification not found with id: " + notificationId));
        notification.setRead(true);
        notification.setReadAt(LocalDateTime.now());
        return mapper.toNotificationDto(notificationRepository.save(notification));
    }

    @Transactional
    public void markAllAsRead(Long userId) {
        List<Notification> unread = notificationRepository.findByUserIdAndIsRead(userId, false);
        unread.forEach(n -> { n.setRead(true); n.setReadAt(LocalDateTime.now()); });
        notificationRepository.saveAll(unread);
    }

    @Transactional
    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }
}