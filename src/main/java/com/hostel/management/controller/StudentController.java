package com.hostel.management.controller;

import com.hostel.management.dto.request.StudentUpdateRequest;
import com.hostel.management.dto.response.StudentDto;
import com.hostel.management.entity.User;
import com.hostel.management.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @GetMapping("/me")
    public ResponseEntity<StudentDto> getMyProfile(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(studentService.getStudentByUserId(user.getId()));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<StudentDto> getStudentByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(studentService.getStudentByUserId(userId));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    public ResponseEntity<List<StudentDto>> searchStudents(@RequestParam String name) {
        return ResponseEntity.ok(studentService.searchStudents(name));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    public ResponseEntity<List<StudentDto>> getStudentsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(studentService.getStudentsByStatus(status));
    }

    /** Accepts a typed request DTO — not a raw Student entity. */
    @PutMapping("/{id}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable Long id,
                                                     @RequestBody StudentUpdateRequest request) {
        return ResponseEntity.ok(studentService.updateStudent(id, request));
    }

    @PutMapping("/{id}/assign-room/{roomId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    public ResponseEntity<StudentDto> assignRoom(@PathVariable Long id,
                                                  @PathVariable Long roomId) {
        return ResponseEntity.ok(studentService.assignRoom(id, roomId));
    }

    @PutMapping("/{id}/vacate-room")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    public ResponseEntity<StudentDto> vacateRoom(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.vacateRoom(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok(Map.of("message", "Student deleted successfully"));
    }

    @GetMapping("/stats")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        return ResponseEntity.ok(studentService.getDashboardStats());
    }
}
