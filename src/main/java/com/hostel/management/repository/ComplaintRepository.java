package com.hostel.management.repository;

import com.hostel.management.entity.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    List<Complaint> findByStudentId(Long studentId);
    List<Complaint> findByStatus(Complaint.ComplaintStatus status);
    List<Complaint> findByCategory(Complaint.ComplaintCategory category);
    List<Complaint> findByPriority(Complaint.Priority priority);
    List<Complaint> findByStudentIdOrderByCreatedAtDesc(Long studentId);

    @Query("SELECT COUNT(c) FROM Complaint c WHERE c.status = 'PENDING'")
    long countPendingComplaints();

    // FIX: JOIN FETCH loads complaint.student in one SQL, preventing
    //      HibernateLazyInitializationException when the mapper accesses complaint.getStudent().
    //      Renamed from findAllOrderByCreatedAtDesc to findAllWithStudentOrderByCreatedAtDesc
    //      to make the fetch behaviour explicit.
    @Query("SELECT c FROM Complaint c LEFT JOIN FETCH c.student s ORDER BY c.createdAt DESC")
    List<Complaint> findAllWithStudentOrderByCreatedAtDesc();

    // Keep old name as alias so any other callers still compile
    @Query("SELECT c FROM Complaint c LEFT JOIN FETCH c.student s ORDER BY c.createdAt DESC")
    List<Complaint> findAllOrderByCreatedAtDesc();
}
