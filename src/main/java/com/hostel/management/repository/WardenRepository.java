package com.hostel.management.repository;

import com.hostel.management.entity.Warden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WardenRepository extends JpaRepository<Warden, Long> {

    Optional<Warden> findByUserId(Long userId);
    Optional<Warden> findByWardenId(String wardenId);
    Optional<Warden> findByEmail(String email);
    boolean existsByWardenId(String wardenId);
    boolean existsByEmail(String email);

    // FIX: JOIN FETCH loads warden.user in one SQL so the mapper can safely
    //      access warden.getUser() without triggering a lazy load outside the session.
    @Query("SELECT w FROM Warden w LEFT JOIN FETCH w.user")
    List<Warden> findAllWithUser();
}
