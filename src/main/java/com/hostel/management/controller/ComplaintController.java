package com.hostel.management.controller;

import com.hostel.management.dto.ComplaintRequest;
import com.hostel.management.dto.response.ComplaintDto;
import com.hostel.management.entity.Student;
import com.hostel.management.entity.User;
import com.hostel.management.service.ComplaintService;
import com.hostel.management.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {

    private final ComplaintService complaintService;
    private final StudentService   studentService;

    public ComplaintController(ComplaintService complaintService, StudentService studentService) {
        this.complaintService = complaintService;
        this.studentService   = studentService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    public ResponseEntity<List<ComplaintDto>> getAllComplaints() {
        return ResponseEntity.ok(complaintService.getAllComplaints());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComplaintDto> getComplaintById(@PathVariable Long id) {
        return ResponseEntity.ok(complaintService.getComplaintById(id));
    }

    @GetMapping("/my")
    public ResponseEntity<List<ComplaintDto>> getMyComplaints(@AuthenticationPrincipal User user) {
        Student student = studentService.getStudentEntityByUserId(user.getId());
        return ResponseEntity.ok(complaintService.getComplaintsByStudent(student.getId()));
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    public ResponseEntity<List<ComplaintDto>> getComplaintsByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(complaintService.getComplaintsByStudent(studentId));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    public ResponseEntity<List<ComplaintDto>> getComplaintsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(complaintService.getComplaintsByStatus(status));
    }

    @GetMapping("/category/{category}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    public ResponseEntity<List<ComplaintDto>> getComplaintsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(complaintService.getComplaintsByCategory(category));
    }

    @PostMapping
    public ResponseEntity<ComplaintDto> createComplaint(@Valid @RequestBody ComplaintRequest request,
                                                         @AuthenticationPrincipal User user) {
        Student student = studentService.getStudentEntityByUserId(user.getId());
        return ResponseEntity.ok(complaintService.createComplaint(request, student.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComplaintDto> updateComplaint(@PathVariable Long id,
                                                         @Valid @RequestBody ComplaintRequest request,
                                                         @AuthenticationPrincipal User user) {
        Student student = studentService.getStudentEntityByUserId(user.getId());
        return ResponseEntity.ok(complaintService.updateComplaint(id, request, student.getId()));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    public ResponseEntity<ComplaintDto> updateStatus(@PathVariable Long id,
                                                      @RequestParam String status,
                                                      @RequestParam(required = false) String remarks) {
        return ResponseEntity.ok(complaintService.updateComplaintStatus(id, status, remarks));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    public ResponseEntity<Map<String, String>> deleteComplaint(@PathVariable Long id) {
        complaintService.deleteComplaint(id);
        return ResponseEntity.ok(Map.of("message", "Complaint deleted successfully"));
    }

    @GetMapping("/stats/pending")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    public ResponseEntity<Map<String, Long>> getPendingCount() {
        return ResponseEntity.ok(Map.of("pendingComplaints",
                complaintService.countPendingComplaints()));
    }
}
