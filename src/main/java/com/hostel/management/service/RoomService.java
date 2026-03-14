package com.hostel.management.service;

import com.hostel.management.dto.request.RoomRequest;
import com.hostel.management.dto.response.RoomDto;
import com.hostel.management.entity.Room;
import com.hostel.management.exception.GlobalExceptionHandler.ResourceNotFoundException;
import com.hostel.management.mapper.EntityMapper;
import com.hostel.management.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final EntityMapper   mapper;

    public RoomService(RoomRepository roomRepository, EntityMapper mapper) {
        this.roomRepository = roomRepository;
        this.mapper         = mapper;
    }

    public Room getRoomEntityById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<RoomDto> getAllRooms() {
        return mapper.toRoomDtoList(roomRepository.findAllWithStudents());
    }

    @Transactional(readOnly = true)
    public RoomDto getRoomById(Long id) {
        return mapper.toRoomDto(getRoomEntityById(id));
    }

    @Transactional(readOnly = true)
    public RoomDto getRoomByNumber(String roomNumber) {
        Room room = roomRepository.findByRoomNumber(roomNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found: " + roomNumber));
        return mapper.toRoomDto(room);
    }

    @Transactional(readOnly = true)
    public List<RoomDto> getAvailableRooms() {
        return mapper.toRoomDtoList(roomRepository.findAvailableRooms());
    }

    @Transactional(readOnly = true)
    public List<RoomDto> getRoomsByBlock(String block) {
        return mapper.toRoomDtoList(roomRepository.findByBlock(block.toUpperCase()));
    }

    @Transactional(readOnly = true)
    public List<RoomDto> getRoomsByType(String type) {
        return mapper.toRoomDtoList(
                roomRepository.findByRoomType(Room.RoomType.valueOf(type.toUpperCase())));
    }

    @Transactional(readOnly = true)
    public List<RoomDto> getRoomsByStatus(String status) {
        return mapper.toRoomDtoList(
                roomRepository.findByStatus(Room.RoomStatus.valueOf(status.toUpperCase())));
    }

    @Transactional
    public RoomDto createRoom(RoomRequest request) {
        if (roomRepository.existsByRoomNumber(request.getRoomNumber())) {
            throw new RuntimeException("Room number " + request.getRoomNumber() + " already exists");
        }
        Room room = new Room();
        mapper.applyRoomRequest(request, room);
        room.setOccupiedCount(0);
        if (room.getStatus() == null) room.setStatus(Room.RoomStatus.AVAILABLE);
        return mapper.toRoomDto(roomRepository.save(room));
    }

    @Transactional
    public RoomDto updateRoom(Long id, RoomRequest request) {
        Room existing = getRoomEntityById(id);
        mapper.applyRoomRequest(request, existing);
        return mapper.toRoomDto(roomRepository.save(existing));
    }

    @Transactional
    public RoomDto updateRoomStatus(Long id, String status) {
        Room room = getRoomEntityById(id);
        room.setStatus(Room.RoomStatus.valueOf(status.toUpperCase()));
        return mapper.toRoomDto(roomRepository.save(room));
    }

    @Transactional
    public void deleteRoom(Long id) {
        Room room = getRoomEntityById(id);
        if (room.getOccupiedCount() != null && room.getOccupiedCount() > 0) {
            throw new RuntimeException("Cannot delete a room that has students assigned");
        }
        roomRepository.delete(room);
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getRoomStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalRooms",          roomRepository.count());
        stats.put("availableRooms",      roomRepository.countAvailableRooms());
        stats.put("totalAvailableSlots", roomRepository.getTotalAvailableSlots());
        return stats;
    }
}
