package com.hostel.management.controller;

import com.hostel.management.dto.request.MessMenuRequest;
import com.hostel.management.dto.response.MessMenuDto;
import com.hostel.management.service.MessService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mess")
public class MessController {

    private final MessService messService;

    public MessController(MessService messService) {
        this.messService = messService;
    }

    @GetMapping("/menu")
    public ResponseEntity<List<MessMenuDto>> getFullMenu() {
        return ResponseEntity.ok(messService.getFullMenu());
    }

    @GetMapping("/menu/weekly")
    public ResponseEntity<Map<String, List<MessMenuDto>>> getWeeklyMenu() {
        return ResponseEntity.ok(messService.getWeeklyMenu());
    }

    @GetMapping("/menu/today")
    public ResponseEntity<List<MessMenuDto>> getTodayMenu() {
        return ResponseEntity.ok(messService.getTodayMenu());
    }

    @GetMapping("/menu/day/{day}")
    public ResponseEntity<List<MessMenuDto>> getMenuByDay(@PathVariable String day) {
        return ResponseEntity.ok(messService.getMenuByDay(day));
    }

    @GetMapping("/menu/meal/{mealType}")
    public ResponseEntity<List<MessMenuDto>> getMenuByMealType(@PathVariable String mealType) {
        return ResponseEntity.ok(messService.getMenuByMealType(mealType));
    }

    @GetMapping("/menu/{id}")
    public ResponseEntity<MessMenuDto> getMenuById(@PathVariable Long id) {
        return ResponseEntity.ok(messService.getMenuById(id));
    }

    @PostMapping("/menu")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    public ResponseEntity<MessMenuDto> createMenuItem(@Valid @RequestBody MessMenuRequest request) {
        return ResponseEntity.ok(messService.createMenuItem(request));
    }

    @PutMapping("/menu/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    public ResponseEntity<MessMenuDto> updateMenuItem(@PathVariable Long id,
                                                       @Valid @RequestBody MessMenuRequest request) {
        return ResponseEntity.ok(messService.updateMenuItem(id, request));
    }

    @DeleteMapping("/menu/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    public ResponseEntity<Map<String, String>> deleteMenuItem(@PathVariable Long id) {
        messService.deleteMenuItem(id);
        return ResponseEntity.ok(Map.of("message", "Menu item deleted successfully"));
    }
}
