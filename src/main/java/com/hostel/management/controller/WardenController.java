package com.hostel.management.controller;

import com.hostel.management.dto.response.WardenDto;
import com.hostel.management.entity.User;
import com.hostel.management.entity.Warden;
import com.hostel.management.service.WardenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/wardens")
public class WardenController {

    private final WardenService wardenService;

    public WardenController(WardenService wardenService) {
        this.wardenService = wardenService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<WardenDto>> getAllWardens() {
        return ResponseEntity.ok(wardenService.getAllWardens());
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    public ResponseEntity<WardenDto> getMyProfile(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(wardenService.getWardenByUserId(user.getId()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<WardenDto> getWardenById(@PathVariable Long id) {
        return ResponseEntity.ok(wardenService.getWardenById(id));
    }

    @PutMapping("/me")
    @PreAuthorize("hasAnyRole('WARDEN', 'ADMIN')")
    public ResponseEntity<WardenDto> updateMyProfile(@AuthenticationPrincipal User user,
                                                      @RequestBody Warden warden) {
        return ResponseEntity.ok(wardenService.updateOwnProfile(user.getId(), warden));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<WardenDto> updateWarden(@PathVariable Long id,
                                                   @RequestBody Warden warden) {
        return ResponseEntity.ok(wardenService.updateWarden(id, warden));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> deleteWarden(@PathVariable Long id) {
        wardenService.deleteWarden(id);
        return ResponseEntity.ok(Map.of("message", "Warden deleted successfully"));
    }
}
