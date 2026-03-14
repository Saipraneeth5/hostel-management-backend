package com.hostel.management.repository;

import com.hostel.management.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByStudentId(Long studentId);
    List<Payment> findByStatus(Payment.PaymentStatus status);
    List<Payment> findByStudentIdAndStatus(Long studentId, Payment.PaymentStatus status);
    Optional<Payment> findByTransactionId(String transactionId);
    List<Payment> findByStudentIdOrderByCreatedAtDesc(Long studentId);

    @Query("SELECT COUNT(p) FROM Payment p WHERE p.status = 'PENDING' OR p.status = 'OVERDUE'")
    long countPendingPayments();

    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.status = 'PAID'")
    BigDecimal getTotalCollected();

    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.status = 'PENDING' OR p.status = 'OVERDUE'")
    BigDecimal getTotalPending();

    // FIX: JOIN FETCH loads payment.student in one SQL, preventing
    //      HibernateLazyInitializationException when the mapper calls payment.getStudent().
    @Query("SELECT p FROM Payment p LEFT JOIN FETCH p.student s ORDER BY p.createdAt DESC")
    List<Payment> findAllWithStudent();
}
