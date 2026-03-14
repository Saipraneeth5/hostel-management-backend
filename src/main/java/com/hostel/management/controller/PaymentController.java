package com.hostel.management.controller;

import com.hostel.management.dto.request.PaymentRequest;
import com.hostel.management.dto.response.PaymentDto;
import com.hostel.management.entity.Payment;
import com.hostel.management.entity.Student;
import com.hostel.management.entity.User;
import com.hostel.management.service.PaymentService;
import com.hostel.management.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final StudentService studentService;

    public PaymentController(PaymentService paymentService, StudentService studentService) {
        this.paymentService = paymentService;
        this.studentService = studentService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    public ResponseEntity<List<PaymentDto>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> getPaymentById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }

    @GetMapping("/my")
    public ResponseEntity<List<PaymentDto>> getMyPayments(@AuthenticationPrincipal User user) {
        Student student = studentService.getStudentEntityByUserId(user.getId());
        return ResponseEntity.ok(paymentService.getPaymentsByStudent(student.getId()));
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    public ResponseEntity<List<PaymentDto>> getPaymentsByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(paymentService.getPaymentsByStudent(studentId));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    public ResponseEntity<List<PaymentDto>> getPaymentsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(paymentService.getPaymentsByStatus(status));
    }

    @PostMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    public ResponseEntity<PaymentDto> createPayment(@Valid @RequestBody PaymentRequest request,
                                                     @PathVariable Long studentId) {
        return ResponseEntity.ok(paymentService.createPayment(request, studentId));
    }

    @PutMapping("/{id}/pay")
    public ResponseEntity<PaymentDto> markAsPaid(@PathVariable Long id,
                                                  @RequestParam Payment.PaymentMethod method) {
        return ResponseEntity.ok(paymentService.markAsPaid(id, method));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    public ResponseEntity<PaymentDto> updateStatus(@PathVariable Long id,
                                                    @RequestParam String status) {
        return ResponseEntity.ok(paymentService.updatePaymentStatus(id, status));
    }

    @PostMapping("/generate-monthly")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    public ResponseEntity<Map<String, String>> generateMonthlyFees(@RequestParam String month) {
        paymentService.generateMonthlyFees(month);
        return ResponseEntity.ok(Map.of("message", "Monthly fees generated for " + month));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.ok(Map.of("message", "Payment record deleted"));
    }

    @GetMapping("/stats")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    public ResponseEntity<Map<String, Object>> getPaymentStats() {
        return ResponseEntity.ok(paymentService.getPaymentStats());
    }
}
