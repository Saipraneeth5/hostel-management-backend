package com.hostel.management.service;

import com.hostel.management.dto.request.PaymentRequest;
import com.hostel.management.dto.response.PaymentDto;
import com.hostel.management.entity.Payment;
import com.hostel.management.entity.Student;
import com.hostel.management.exception.GlobalExceptionHandler.ResourceNotFoundException;
import com.hostel.management.mapper.EntityMapper;
import com.hostel.management.repository.PaymentRepository;
import com.hostel.management.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final StudentRepository studentRepository;
    private final EntityMapper      mapper;

    public PaymentService(PaymentRepository paymentRepository,
                          StudentRepository studentRepository,
                          EntityMapper mapper) {
        this.paymentRepository = paymentRepository;
        this.studentRepository = studentRepository;
        this.mapper            = mapper;
    }

    // FIX: @Transactional(readOnly=true) keeps session open so payment.getStudent()
    //      (LAZY @ManyToOne) can be resolved in the mapper without LazyInitializationException.
    @Transactional(readOnly = true)
    public List<PaymentDto> getAllPayments() {
        // JOIN FETCH loads student in one query — eliminates N+1
        return mapper.toPaymentDtoList(paymentRepository.findAllWithStudent());
    }

    @Transactional(readOnly = true)
    public PaymentDto getPaymentById(Long id) {
        return mapper.toPaymentDto(getPaymentEntityById(id));
    }

    @Transactional(readOnly = true)
    public List<PaymentDto> getPaymentsByStudent(Long studentId) {
        return mapper.toPaymentDtoList(
                paymentRepository.findByStudentIdOrderByCreatedAtDesc(studentId));
    }

    @Transactional(readOnly = true)
    public List<PaymentDto> getPaymentsByStatus(String status) {
        return mapper.toPaymentDtoList(
                paymentRepository.findByStatus(
                        Payment.PaymentStatus.valueOf(status.toUpperCase())));
    }

    @Transactional
    public PaymentDto createPayment(PaymentRequest request, Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Student not found with id: " + studentId));
        Payment payment = new Payment();
        mapper.applyPaymentRequest(request, payment);
        payment.setStudent(student);
        payment.setTransactionId(generateTransactionId());
        payment.setStatus(Payment.PaymentStatus.PENDING);
        if (payment.getDueDate() == null) {
            payment.setDueDate(LocalDate.now().plusDays(30));
        }
        return mapper.toPaymentDto(paymentRepository.save(payment));
    }

    @Transactional
    public PaymentDto markAsPaid(Long id, Payment.PaymentMethod method) {
        Payment payment = getPaymentEntityById(id);
        if (payment.getStatus() == Payment.PaymentStatus.PAID) {
            throw new RuntimeException("Payment is already marked as paid");
        }
        payment.setStatus(Payment.PaymentStatus.PAID);
        payment.setPaymentMethod(method);
        payment.setPaymentDate(LocalDate.now());
        payment.setReceipt("RCP-" + System.currentTimeMillis());
        return mapper.toPaymentDto(paymentRepository.save(payment));
    }

    @Transactional
    public PaymentDto updatePaymentStatus(Long id, String status) {
        Payment payment = getPaymentEntityById(id);
        payment.setStatus(Payment.PaymentStatus.valueOf(status.toUpperCase()));
        if (status.equalsIgnoreCase("PAID")) {
            payment.setPaymentDate(LocalDate.now());
        }
        return mapper.toPaymentDto(paymentRepository.save(payment));
    }

    @Transactional
    public void generateMonthlyFees(String month) {
        List<Student> students = studentRepository.findByStatus(Student.StudentStatus.ACTIVE);
        for (Student student : students) {
            if (student.getRoom() == null) continue;
            boolean alreadyGenerated = paymentRepository
                    .findByStudentId(student.getId()).stream()
                    .anyMatch(p -> month.equals(p.getForMonth())
                            && p.getPaymentType() == Payment.PaymentType.ROOM_RENT);
            if (!alreadyGenerated) {
                Payment payment = Payment.builder()
                        .student(student)
                        .amount(student.getRoom().getMonthlyFee())
                        .paymentType(Payment.PaymentType.ROOM_RENT)
                        .status(Payment.PaymentStatus.PENDING)
                        .forMonth(month)
                        .dueDate(LocalDate.now().plusDays(10))
                        .description("Room rent for " + month)
                        .transactionId(generateTransactionId())
                        .build();
                paymentRepository.save(payment);
            }
        }
    }

    @Transactional
    public void deletePayment(Long id) {
        paymentRepository.delete(getPaymentEntityById(id));
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getPaymentStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalPayments",   paymentRepository.count());
        stats.put("pendingPayments", paymentRepository.countPendingPayments());
        BigDecimal collected = paymentRepository.getTotalCollected();
        BigDecimal pending   = paymentRepository.getTotalPending();
        stats.put("totalCollected", collected != null ? collected : BigDecimal.ZERO);
        stats.put("totalPending",   pending   != null ? pending   : BigDecimal.ZERO);
        return stats;
    }

    // ── Internal helper ───────────────────────────────────────────────────────
    private Payment getPaymentEntityById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Payment not found with id: " + id));
    }

    private String generateTransactionId() {
        return "TXN-" + UUID.randomUUID().toString()
                .replace("-", "").substring(0, 12).toUpperCase();
    }
}
