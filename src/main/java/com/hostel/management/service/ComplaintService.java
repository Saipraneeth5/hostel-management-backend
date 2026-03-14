package com.hostel.management.service;

import com.hostel.management.dto.ComplaintRequest;
import com.hostel.management.dto.response.ComplaintDto;
import com.hostel.management.entity.Complaint;
import com.hostel.management.entity.Student;
import com.hostel.management.exception.GlobalExceptionHandler.ResourceNotFoundException;
import com.hostel.management.mapper.EntityMapper;
import com.hostel.management.repository.ComplaintRepository;
import com.hostel.management.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final StudentRepository   studentRepository;
    private final EntityMapper        mapper;

    public ComplaintService(ComplaintRepository complaintRepository,
                            StudentRepository studentRepository,
                            EntityMapper mapper) {
        this.complaintRepository = complaintRepository;
        this.studentRepository   = studentRepository;
        this.mapper              = mapper;
    }

    // FIX: @Transactional(readOnly=true) keeps session open so complaint.getStudent()
    //      (which is LAZY) can be resolved inside the mapper without throwing
    //      HibernateLazyInitializationException.
    @Transactional(readOnly = true)
    public List<ComplaintDto> getAllComplaints() {
        return mapper.toComplaintDtoList(complaintRepository.findAllWithStudentOrderByCreatedAtDesc());
    }

    @Transactional(readOnly = true)
    public ComplaintDto getComplaintById(Long id) {
        return mapper.toComplaintDto(getComplaintEntityById(id));
    }

    @Transactional(readOnly = true)
    public List<ComplaintDto> getComplaintsByStudent(Long studentId) {
        return mapper.toComplaintDtoList(
                complaintRepository.findByStudentIdOrderByCreatedAtDesc(studentId));
    }

    @Transactional(readOnly = true)
    public List<ComplaintDto> getComplaintsByStatus(String status) {
        return mapper.toComplaintDtoList(
                complaintRepository.findByStatus(
                        Complaint.ComplaintStatus.valueOf(status.toUpperCase())));
    }

    @Transactional(readOnly = true)
    public List<ComplaintDto> getComplaintsByCategory(String category) {
        return mapper.toComplaintDtoList(
                complaintRepository.findByCategory(
                        Complaint.ComplaintCategory.valueOf(category.toUpperCase())));
    }

    @Transactional
    public ComplaintDto createComplaint(ComplaintRequest request, Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Student not found with id: " + studentId));
        Complaint complaint = Complaint.builder()
                .student(student)
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory())
                .priority(request.getPriority() != null
                        ? request.getPriority() : Complaint.Priority.MEDIUM)
                .status(Complaint.ComplaintStatus.PENDING)
                .build();
        return mapper.toComplaintDto(complaintRepository.save(complaint));
    }

    @Transactional
    public ComplaintDto updateComplaintStatus(Long id, String status, String remarks) {
        Complaint complaint = getComplaintEntityById(id);
        complaint.setStatus(Complaint.ComplaintStatus.valueOf(status.toUpperCase()));
        complaint.setAdminRemarks(remarks);
        if (status.equalsIgnoreCase("RESOLVED")) {
            complaint.setResolvedAt(LocalDateTime.now());
        }
        return mapper.toComplaintDto(complaintRepository.save(complaint));
    }

    @Transactional
    public ComplaintDto updateComplaint(Long id, ComplaintRequest request, Long studentId) {
        Complaint complaint = getComplaintEntityById(id);
        if (!complaint.getStudent().getId().equals(studentId)) {
            throw new RuntimeException("You can only update your own complaints");
        }
        if (complaint.getStatus() != Complaint.ComplaintStatus.PENDING) {
            throw new RuntimeException("Cannot update a complaint that is already being processed");
        }
        complaint.setTitle(request.getTitle());
        complaint.setDescription(request.getDescription());
        complaint.setCategory(request.getCategory());
        complaint.setPriority(request.getPriority());
        return mapper.toComplaintDto(complaintRepository.save(complaint));
    }

    @Transactional
    public void deleteComplaint(Long id) {
        complaintRepository.delete(getComplaintEntityById(id));
    }

    @Transactional(readOnly = true)
    public long countPendingComplaints() {
        return complaintRepository.countPendingComplaints();
    }

    // ── Internal helper ───────────────────────────────────────────────────────
    private Complaint getComplaintEntityById(Long id) {
        return complaintRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Complaint not found with id: " + id));
    }
}
