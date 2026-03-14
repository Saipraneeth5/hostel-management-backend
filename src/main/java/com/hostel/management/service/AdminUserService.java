package com.hostel.management.service;

import com.hostel.management.dto.CreateStudentRequest;
import com.hostel.management.dto.CreateWardenRequest;
import com.hostel.management.entity.Room;
import com.hostel.management.entity.Student;
import com.hostel.management.entity.User;
import com.hostel.management.entity.Warden;
import com.hostel.management.exception.GlobalExceptionHandler.ResourceNotFoundException;
import com.hostel.management.repository.RoomRepository;
import com.hostel.management.repository.StudentRepository;
import com.hostel.management.repository.UserRepository;
import com.hostel.management.repository.WardenRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class AdminUserService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final WardenRepository wardenRepository;
    private final RoomRepository roomRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminUserService(UserRepository userRepository,
                            StudentRepository studentRepository,
                            WardenRepository wardenRepository,
                            RoomRepository roomRepository,
                            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.wardenRepository = wardenRepository;
        this.roomRepository = roomRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ── Create Student ─────────────────────────────────────────────────────────
    @Transactional
    public Map<String, Object> createStudent(CreateStudentRequest req) {
        validateCredentials(req.getUsername(), req.getEmail());

        // 1. Save User first — get the generated PK
        User user = new User();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole(User.Role.STUDENT);
        user = userRepository.save(user);

        // 2. Determine student code
        String studentCode = (req.getStudentId() != null && !req.getStudentId().isBlank())
                ? req.getStudentId()
                : "STU" + System.currentTimeMillis();

        if (studentRepository.existsByStudentId(studentCode)) {
            throw new RuntimeException("Student ID '" + studentCode + "' already exists");
        }

        // 3. Build Student profile using the saved user
        Student student = new Student();
        student.setStudentId(studentCode);
        student.setFirstName(req.getFirstName());
        student.setLastName(req.getLastName());
        student.setEmail(req.getEmail());
        student.setPhoneNumber(req.getPhoneNumber());
        student.setParentName(req.getParentName());
        student.setParentPhone(req.getParentPhone());
        student.setCourse(req.getCourse());
        student.setBranch(req.getBranch());
        student.setYear(req.getYear());
        student.setAddress(req.getAddress());
        student.setCity(req.getCity());
        student.setState(req.getState());
        student.setAdmissionDate(LocalDate.now());
        student.setStatus(Student.StudentStatus.ACTIVE);
        student.setUser(user);

        if (req.getDateOfBirth() != null && !req.getDateOfBirth().isBlank()) {
            student.setDateOfBirth(LocalDate.parse(req.getDateOfBirth()));
        }

        // 4. Assign room if provided — validate vacancy before saving student
        if (req.getRoomId() != null) {
            Room room = roomRepository.findById(req.getRoomId())
                    .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + req.getRoomId()));
            if (!room.hasVacancy()) {
                throw new RuntimeException("Room " + room.getRoomNumber() + " is full");
            }
            if (room.getStatus() != Room.RoomStatus.AVAILABLE) {
                throw new RuntimeException("Room " + room.getRoomNumber() + " is not available");
            }
            student.setRoom(room);
            room.setOccupiedCount(room.getOccupiedCount() + 1);
            if (room.getOccupiedCount() >= room.getCapacity()) {
                room.setStatus(Room.RoomStatus.FULL);
            }
            roomRepository.save(room);
        }

        student = studentRepository.save(student);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Student created successfully");
        response.put("studentId", student.getId());
        response.put("studentCode", student.getStudentId());
        response.put("username", user.getUsername());
        response.put("userId", user.getId());
        return response;
    }

    // ── Create Warden ──────────────────────────────────────────────────────────
    @Transactional
    public Map<String, Object> createWarden(CreateWardenRequest req) {
        validateCredentials(req.getUsername(), req.getEmail());

        // 1. Save User first
        User user = new User();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole(User.Role.WARDEN);
        user = userRepository.save(user);

        // 2. Determine warden code
        String wardenCode = (req.getWardenId() != null && !req.getWardenId().isBlank())
                ? req.getWardenId()
                : "WRD" + System.currentTimeMillis();

        if (wardenRepository.existsByWardenId(wardenCode)) {
            throw new RuntimeException("Warden ID '" + wardenCode + "' already exists");
        }

        // 3. Build Warden profile
        Warden warden = new Warden();
        warden.setWardenId(wardenCode);
        warden.setFirstName(req.getFirstName());
        warden.setLastName(req.getLastName());
        warden.setEmail(req.getEmail());
        warden.setPhoneNumber(req.getPhoneNumber());
        warden.setAssignedBlock(req.getAssignedBlock());
        warden.setQualification(req.getQualification());
        warden.setAddress(req.getAddress());
        warden.setCity(req.getCity());
        warden.setState(req.getState());
        warden.setStatus(Warden.WardenStatus.ACTIVE);
        warden.setUser(user);

        if (req.getDateOfBirth() != null && !req.getDateOfBirth().isBlank()) {
            warden.setDateOfBirth(LocalDate.parse(req.getDateOfBirth()));
        }

        warden = wardenRepository.save(warden);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Warden created successfully");
        response.put("wardenId", warden.getId());
        response.put("wardenCode", warden.getWardenId());
        response.put("username", user.getUsername());
        response.put("userId", user.getId());
        return response;
    }

    // ── Shared validation ──────────────────────────────────────────────────────
    private void validateCredentials(String username, String email) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username '" + username + "' is already taken");
        }
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email '" + email + "' is already registered");
        }
    }
}
