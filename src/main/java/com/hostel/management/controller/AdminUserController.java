package com.hostel.management.controller;

import com.hostel.management.dto.CreateStudentRequest;
import com.hostel.management.dto.CreateWardenRequest;
import com.hostel.management.service.AdminUserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    private final AdminUserService adminUserService;

    public AdminUserController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    @PostMapping("/student")
    public ResponseEntity<Map<String, Object>> createStudent(
            @Valid @RequestBody CreateStudentRequest request) {
        return ResponseEntity.ok(adminUserService.createStudent(request));
    }

    @PostMapping("/warden")
    public ResponseEntity<Map<String, Object>> createWarden(
            @Valid @RequestBody CreateWardenRequest request) {
        return ResponseEntity.ok(adminUserService.createWarden(request));
    }
}
