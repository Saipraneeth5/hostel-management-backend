package com.hostel.management.repository;

import com.hostel.management.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUserId(Long userId);
    List<Notification> findByUserIdAndIsRead(Long userId, boolean isRead);
    List<Notification> findByIsGlobal(boolean isGlobal);

    @Query("SELECT n FROM Notification n LEFT JOIN FETCH n.user WHERE (n.user IS NOT NULL AND n.user.id = :userId) OR n.isGlobal = true ORDER BY n.createdAt DESC")
    List<Notification> findAllForUser(@Param("userId") Long userId);

    @Query("SELECT n FROM Notification n LEFT JOIN FETCH n.user")
    List<Notification> findAllWithUser();

    @Query("SELECT COUNT(n) FROM Notification n WHERE n.user IS NOT NULL AND n.user.id = :userId AND n.isRead = false")
    long countUnreadByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(n) FROM Notification n WHERE ((n.user IS NOT NULL AND n.user.id = :userId) OR n.isGlobal = true) AND n.isRead = false")
    long countUnreadForUser(@Param("userId") Long userId);
}
