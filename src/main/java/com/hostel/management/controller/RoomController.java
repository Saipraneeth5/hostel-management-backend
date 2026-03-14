package com.hostel.management.controller;

import com.hostel.management.dto.request.RoomRequest;
import com.hostel.management.dto.response.RoomDto;
import com.hostel.management.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity<List<RoomDto>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDto> getRoomById(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.getRoomById(id));
    }

    @GetMapping("/number/{roomNumber}")
    public ResponseEntity<RoomDto> getRoomByNumber(@PathVariable String roomNumber) {
        return ResponseEntity.ok(roomService.getRoomByNumber(roomNumber));
    }

    @GetMapping("/available")
    public ResponseEntity<List<RoomDto>> getAvailableRooms() {
        return ResponseEntity.ok(roomService.getAvailableRooms());
    }

    @GetMapping("/block/{block}")
    public ResponseEntity<List<RoomDto>> getRoomsByBlock(@PathVariable String block) {
        return ResponseEntity.ok(roomService.getRoomsByBlock(block));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<RoomDto>> getRoomsByType(@PathVariable String type) {
        return ResponseEntity.ok(roomService.getRoomsByType(type));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    public ResponseEntity<List<RoomDto>> getRoomsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(roomService.getRoomsByStatus(status));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    public ResponseEntity<RoomDto> createRoom(@Valid @RequestBody RoomRequest request) {
        return ResponseEntity.ok(roomService.createRoom(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    public ResponseEntity<RoomDto> updateRoom(@PathVariable Long id,
                                               @Valid @RequestBody RoomRequest request) {
        return ResponseEntity.ok(roomService.updateRoom(id, request));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    public ResponseEntity<RoomDto> updateRoomStatus(@PathVariable Long id,
                                                     @RequestParam String status) {
        return ResponseEntity.ok(roomService.updateRoomStatus(id, status));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.ok(Map.of("message", "Room deleted successfully"));
    }

    @GetMapping("/stats")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    public ResponseEntity<Map<String, Object>> getRoomStats() {
        return ResponseEntity.ok(roomService.getRoomStats());
    }
}
