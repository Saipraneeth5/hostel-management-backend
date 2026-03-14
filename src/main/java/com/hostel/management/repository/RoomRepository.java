package com.hostel.management.repository;

import com.hostel.management.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByRoomNumber(String roomNumber);
    List<Room> findByStatus(Room.RoomStatus status);
    List<Room> findByBlock(String block);
    List<Room> findByRoomType(Room.RoomType roomType);
    List<Room> findByFloor(Integer floor);
    boolean existsByRoomNumber(String roomNumber);
    
    @Query("SELECT DISTINCT r FROM Room r LEFT JOIN FETCH r.students")
    List<Room> findAllWithStudents();
    
    @Query("SELECT r FROM Room r WHERE r.occupiedCount < r.capacity AND r.status = 'AVAILABLE'")
    List<Room> findAvailableRooms();

    @Query("SELECT COUNT(r) FROM Room r WHERE r.status = 'AVAILABLE'")
    long countAvailableRooms();

    @Query("SELECT SUM(r.capacity - r.occupiedCount) FROM Room r WHERE r.status = 'AVAILABLE'")
    Long getTotalAvailableSlots();
}
