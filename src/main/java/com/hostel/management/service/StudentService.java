package com.hostel.management.service;

import com.hostel.management.dto.request.StudentUpdateRequest;
import com.hostel.management.dto.response.StudentDto;
import com.hostel.management.entity.Room;
import com.hostel.management.entity.Student;
import com.hostel.management.exception.GlobalExceptionHandler.ResourceNotFoundException;
import com.hostel.management.mapper.EntityMapper;
import com.hostel.management.repository.RoomRepository;
import com.hostel.management.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final RoomRepository    roomRepository;
    private final EntityMapper      mapper;

    public StudentService(StudentRepository studentRepository,
                          RoomRepository roomRepository,
                          EntityMapper mapper) {
        this.studentRepository = studentRepository;
        this.roomRepository    = roomRepository;
        this.mapper            = mapper;
    }

    // ── Internal entity helpers ───────────────────────────────────────────────

    @Transactional(readOnly = true)
    public Student getStudentEntityById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public Student getStudentEntityByUserId(Long userId) {
        return studentRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Student profile not found for userId: " + userId));
    }

    // ── Public DTO-returning API ──────────────────────────────────────────────

    // FIX: @Transactional(readOnly=true) keeps the JPA session open so the mapper
    //      can safely dereference lazy associations (student.getUser(), student.getRoom()).
    //      Without this the session closes after findAll() and any lazy access in the
    //      mapper throws HibernateLazyInitializationException.
    @Transactional(readOnly = true)
    public List<StudentDto> getAllStudents() {
        // JOIN FETCH loads room+user in one SQL — eliminates N+1 queries
        return mapper.toStudentDtoList(studentRepository.findAllWithRoomAndUser());
    }

    @Transactional(readOnly = true)
    public StudentDto getStudentById(Long id) {
        return mapper.toStudentDto(
                studentRepository.findByIdWithRoomAndUser(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id)));
    }

    @Transactional(readOnly = true)
    public StudentDto getStudentByUserId(Long userId) {
        return mapper.toStudentDto(getStudentEntityByUserId(userId));
    }

    @Transactional(readOnly = true)
    public List<StudentDto> searchStudents(String name) {
        return mapper.toStudentDtoList(studentRepository.searchByName(name));
    }

    @Transactional(readOnly = true)
    public List<StudentDto> getStudentsByStatus(String status) {
        return mapper.toStudentDtoList(
                studentRepository.findByStatusWithRoomAndUser(
                        Student.StudentStatus.valueOf(status.toUpperCase())));
    }

    @Transactional
    public StudentDto updateStudent(Long id, StudentUpdateRequest request) {
        Student existing = getStudentEntityById(id);
        mapper.applyStudentUpdateRequest(request, existing);
        return mapper.toStudentDto(studentRepository.save(existing));
    }

    @Transactional
    public StudentDto assignRoom(Long studentId, Long roomId) {
        Student student = getStudentEntityById(studentId);
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + roomId));

        if (!room.hasVacancy()) {
            throw new RuntimeException("Room " + room.getRoomNumber() + " is full");
        }
        if (room.getStatus() != Room.RoomStatus.AVAILABLE) {
            throw new RuntimeException("Room " + room.getRoomNumber() + " is not available");
        }

        if (student.getRoom() != null) {
            Room oldRoom = student.getRoom();
            oldRoom.setOccupiedCount(Math.max(0, oldRoom.getOccupiedCount() - 1));
            if (oldRoom.getOccupiedCount() < oldRoom.getCapacity()) {
                oldRoom.setStatus(Room.RoomStatus.AVAILABLE);
            }
            roomRepository.save(oldRoom);
        }

        student.setRoom(room);
        room.setOccupiedCount(room.getOccupiedCount() + 1);
        if (room.getOccupiedCount() >= room.getCapacity()) {
            room.setStatus(Room.RoomStatus.FULL);
        }
        roomRepository.save(room);
        return mapper.toStudentDto(studentRepository.save(student));
    }

    @Transactional
    public StudentDto vacateRoom(Long studentId) {
        Student student = getStudentEntityById(studentId);
        if (student.getRoom() == null) {
            throw new RuntimeException("Student is not assigned to any room");
        }
        Room room = student.getRoom();
        room.setOccupiedCount(Math.max(0, room.getOccupiedCount() - 1));
        if (room.getStatus() == Room.RoomStatus.FULL) {
            room.setStatus(Room.RoomStatus.AVAILABLE);
        }
        roomRepository.save(room);
        student.setRoom(null);
        return mapper.toStudentDto(studentRepository.save(student));
    }

    @Transactional
    public void deleteStudent(Long id) {
        Student student = getStudentEntityById(id);
        if (student.getRoom() != null) {
            Room room = student.getRoom();
            room.setOccupiedCount(Math.max(0, room.getOccupiedCount() - 1));
            if (room.getStatus() == Room.RoomStatus.FULL) {
                room.setStatus(Room.RoomStatus.AVAILABLE);
            }
            roomRepository.save(room);
            student.setRoom(null);
            studentRepository.save(student);
        }
        studentRepository.delete(student);
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalStudents",  studentRepository.count());
        stats.put("activeStudents", studentRepository.countActiveStudents());
        return stats;
    }
}
