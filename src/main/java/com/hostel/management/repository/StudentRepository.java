package com.hostel.management.repository;

import com.hostel.management.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByStudentId(String studentId);
    Optional<Student> findByEmail(String email);
    Optional<Student> findByUserId(Long userId);
    List<Student> findByStatus(Student.StudentStatus status);
    List<Student> findByRoomId(Long roomId);
    boolean existsByStudentId(String studentId);
    boolean existsByEmail(String email);

    @Query("SELECT COUNT(s) FROM Student s WHERE s.status = 'ACTIVE'")
    long countActiveStudents();

    @Query("SELECT s FROM Student s WHERE s.firstName LIKE %:name% OR s.lastName LIKE %:name%")
    List<Student> searchByName(@Param("name") String name);

    // FIX: JOIN FETCH loads room and user in a single SQL query.
    //      Without this, the mapper accessing student.getRoom() and student.getUser()
    //      fires a separate SELECT per student (N+1) or crashes if session is closed.
    @Query("SELECT s FROM Student s LEFT JOIN FETCH s.room LEFT JOIN FETCH s.user")
    List<Student> findAllWithRoomAndUser();

    @Query("SELECT s FROM Student s LEFT JOIN FETCH s.room LEFT JOIN FETCH s.user WHERE s.id = :id")
    Optional<Student> findByIdWithRoomAndUser(@Param("id") Long id);

    @Query("SELECT s FROM Student s LEFT JOIN FETCH s.room LEFT JOIN FETCH s.user WHERE s.status = :status")
    List<Student> findByStatusWithRoomAndUser(@Param("status") Student.StudentStatus status);
}
