package com.hostel.management.mapper;

import com.hostel.management.dto.request.MessMenuRequest;
import com.hostel.management.dto.request.PaymentRequest;
import com.hostel.management.dto.request.RoomRequest;
import com.hostel.management.dto.request.StudentUpdateRequest;
import com.hostel.management.dto.response.*;
import com.hostel.management.entity.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Single-responsibility mapper: Entity ↔ DTO.
 *
 * The recursion-breaking strategy used throughout:
 *   Room      → RoomDto      (students list uses StudentSummaryDto — no room field inside)
 *   Student   → StudentDto   (room field uses RoomSummaryDto     — no students list inside)
 *   Complaint → ComplaintDto (student field uses StudentSummaryDto)
 *   Payment   → PaymentDto   (student field uses StudentSummaryDto)
 *
 * No Jackson @JsonManagedReference / @JsonBackReference are needed on entities
 * because entities are never serialised directly by any controller.
 */
@Component
public class EntityMapper {

    // ── User ─────────────────────────────────────────────────────────────────

    public UserDto toUserDto(User user) {
        if (user == null) return null;
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole() != null ? user.getRole().name() : null);
        dto.setActive(user.isActive());
        return dto;
    }

    // ── Student summary (embedded inside Room / Complaint / Payment) ──────────

    public StudentSummaryDto toStudentSummaryDto(Student student) {
        if (student == null) return null;
        StudentSummaryDto dto = new StudentSummaryDto();
        dto.setId(student.getId());
        dto.setStudentId(student.getStudentId());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());
        dto.setEmail(student.getEmail());
        dto.setPhoneNumber(student.getPhoneNumber());
        dto.setCourse(student.getCourse());
        dto.setBranch(student.getBranch());
        dto.setYear(student.getYear());
        dto.setStatus(student.getStatus() != null ? student.getStatus().name() : null);
        return dto;
    }

    // ── Room summary (embedded inside Student) ────────────────────────────────

    public RoomSummaryDto toRoomSummaryDto(Room room) {
        if (room == null) return null;
        RoomSummaryDto dto = new RoomSummaryDto();
        dto.setId(room.getId());
        dto.setRoomNumber(room.getRoomNumber());
        dto.setRoomType(room.getRoomType() != null ? room.getRoomType().name() : null);
        dto.setCapacity(room.getCapacity());
        dto.setOccupiedCount(room.getOccupiedCount());
        dto.setFloor(room.getFloor());
        dto.setBlock(room.getBlock());
        dto.setMonthlyFee(room.getMonthlyFee());
        dto.setStatus(room.getStatus() != null ? room.getStatus().name() : null);
        dto.setHasAc(room.isHasAc());
        dto.setHasAttachedBathroom(room.isHasAttachedBathroom());
        return dto;
    }

    // ── Room (full) ───────────────────────────────────────────────────────────

    public RoomDto toRoomDto(Room room) {
        if (room == null) return null;
        RoomDto dto = new RoomDto();
        dto.setId(room.getId());
        dto.setRoomNumber(room.getRoomNumber());
        dto.setRoomType(room.getRoomType() != null ? room.getRoomType().name() : null);
        dto.setCapacity(room.getCapacity());
        dto.setOccupiedCount(room.getOccupiedCount());
        dto.setFloor(room.getFloor());
        dto.setBlock(room.getBlock());
        dto.setMonthlyFee(room.getMonthlyFee());
        dto.setStatus(room.getStatus() != null ? room.getStatus().name() : null);
        dto.setDescription(room.getDescription());
        dto.setHasAc(room.isHasAc());
        dto.setHasAttachedBathroom(room.isHasAttachedBathroom());
        dto.setCreatedAt(room.getCreatedAt());
        dto.setUpdatedAt(room.getUpdatedAt());
        // Students use the summary DTO — no room field inside → cycle broken
        List<StudentSummaryDto> students = room.getStudents() != null
                ? room.getStudents().stream()
                      .map(this::toStudentSummaryDto)
                      .collect(Collectors.toList())
                : Collections.emptyList();
        dto.setStudents(students);
        return dto;
    }

    public List<RoomDto> toRoomDtoList(List<Room> rooms) {
        if (rooms == null) return Collections.emptyList();
        return rooms.stream().map(this::toRoomDto).collect(Collectors.toList());
    }

    // ── Student (full) ────────────────────────────────────────────────────────

    public StudentDto toStudentDto(Student student) {
        if (student == null) return null;
        StudentDto dto = new StudentDto();
        dto.setId(student.getId());
        dto.setStudentId(student.getStudentId());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());
        dto.setEmail(student.getEmail());
        dto.setPhoneNumber(student.getPhoneNumber());
        dto.setParentPhone(student.getParentPhone());
        dto.setParentName(student.getParentName());
        dto.setDateOfBirth(student.getDateOfBirth());
        dto.setCourse(student.getCourse());
        dto.setBranch(student.getBranch());
        dto.setYear(student.getYear());
        dto.setAdmissionDate(student.getAdmissionDate());
        dto.setCheckOutDate(student.getCheckOutDate());
        dto.setAddress(student.getAddress());
        dto.setCity(student.getCity());
        dto.setState(student.getState());
        dto.setStatus(student.getStatus() != null ? student.getStatus().name() : null);
        dto.setProfilePicture(student.getProfilePicture());
        dto.setCreatedAt(student.getCreatedAt());
        dto.setUpdatedAt(student.getUpdatedAt());
        // Room uses summary DTO — no students list inside → cycle broken
        dto.setRoom(toRoomSummaryDto(student.getRoom()));
        // User uses safe DTO — no password
        dto.setUser(toUserDto(student.getUser()));
        return dto;
    }

    public List<StudentDto> toStudentDtoList(List<Student> students) {
        if (students == null) return Collections.emptyList();
        return students.stream().map(this::toStudentDto).collect(Collectors.toList());
    }

    // ── Complaint ─────────────────────────────────────────────────────────────

    public ComplaintDto toComplaintDto(Complaint complaint) {
        if (complaint == null) return null;
        ComplaintDto dto = new ComplaintDto();
        dto.setId(complaint.getId());
        dto.setTitle(complaint.getTitle());
        dto.setDescription(complaint.getDescription());
        dto.setCategory(complaint.getCategory() != null ? complaint.getCategory().name() : null);
        dto.setStatus(complaint.getStatus() != null ? complaint.getStatus().name() : null);
        dto.setPriority(complaint.getPriority() != null ? complaint.getPriority().name() : null);
        dto.setAdminRemarks(complaint.getAdminRemarks());
        dto.setResolvedAt(complaint.getResolvedAt());
        dto.setCreatedAt(complaint.getCreatedAt());
        dto.setUpdatedAt(complaint.getUpdatedAt());
        // Summary only — no complaints list inside the student → cycle broken
        dto.setStudent(toStudentSummaryDto(complaint.getStudent()));
        return dto;
    }

    public List<ComplaintDto> toComplaintDtoList(List<Complaint> complaints) {
        if (complaints == null) return Collections.emptyList();
        return complaints.stream().map(this::toComplaintDto).collect(Collectors.toList());
    }

    // ── Payment ───────────────────────────────────────────────────────────────

    public PaymentDto toPaymentDto(Payment payment) {
        if (payment == null) return null;
        PaymentDto dto = new PaymentDto();
        dto.setId(payment.getId());
        dto.setTransactionId(payment.getTransactionId());
        dto.setAmount(payment.getAmount());
        dto.setPaymentType(payment.getPaymentType() != null ? payment.getPaymentType().name() : null);
        dto.setStatus(payment.getStatus() != null ? payment.getStatus().name() : null);
        dto.setPaymentMethod(payment.getPaymentMethod() != null ? payment.getPaymentMethod().name() : null);
        dto.setPaymentDate(payment.getPaymentDate());
        dto.setDueDate(payment.getDueDate());
        dto.setForMonth(payment.getForMonth());
        dto.setDescription(payment.getDescription());
        dto.setReceipt(payment.getReceipt());
        dto.setCreatedAt(payment.getCreatedAt());
        dto.setUpdatedAt(payment.getUpdatedAt());
        // Summary only — no payments list inside the student → cycle broken
        dto.setStudent(toStudentSummaryDto(payment.getStudent()));
        return dto;
    }

    public List<PaymentDto> toPaymentDtoList(List<Payment> payments) {
        if (payments == null) return Collections.emptyList();
        return payments.stream().map(this::toPaymentDto).collect(Collectors.toList());
    }

    // ── Notification ──────────────────────────────────────────────────────────

    public NotificationDto toNotificationDto(Notification notification) {
        if (notification == null) return null;
        NotificationDto dto = new NotificationDto();
        dto.setId(notification.getId());
        dto.setTitle(notification.getTitle());
        dto.setMessage(notification.getMessage());
        dto.setNotificationType(
                notification.getNotificationType() != null
                        ? notification.getNotificationType().name() : null);
        dto.setRead(notification.isRead());
        dto.setGlobal(notification.isGlobal());
        dto.setSentBy(notification.getSentBy());
        dto.setCreatedAt(notification.getCreatedAt());
        dto.setReadAt(notification.getReadAt());
        // Only carry userId + username — not the full User graph
        if (notification.getUser() != null) {
            dto.setUserId(notification.getUser().getId());
            dto.setUsername(notification.getUser().getUsername());
        }
        return dto;
    }

    public List<NotificationDto> toNotificationDtoList(List<Notification> notifications) {
        if (notifications == null) return Collections.emptyList();
        return notifications.stream().map(this::toNotificationDto).collect(Collectors.toList());
    }

    // ── MessMenu ──────────────────────────────────────────────────────────────

    public MessMenuDto toMessMenuDto(MessMenu menu) {
        if (menu == null) return null;
        MessMenuDto dto = new MessMenuDto();
        dto.setId(menu.getId());
        dto.setDayOfWeek(menu.getDayOfWeek());
        dto.setMealType(menu.getMealType() != null ? menu.getMealType().name() : null);
        dto.setItems(menu.getItems());
        dto.setDescription(menu.getDescription());
        dto.setSpecial(menu.isSpecial());
        dto.setCreatedAt(menu.getCreatedAt());
        dto.setUpdatedAt(menu.getUpdatedAt());
        return dto;
    }

    public List<MessMenuDto> toMessMenuDtoList(List<MessMenu> menus) {
        if (menus == null) return Collections.emptyList();
        return menus.stream().map(this::toMessMenuDto).collect(Collectors.toList());
    }

    // ── Warden ────────────────────────────────────────────────────────────────

    public WardenDto toWardenDto(Warden warden) {
        if (warden == null) return null;
        WardenDto dto = new WardenDto();
        dto.setId(warden.getId());
        dto.setWardenId(warden.getWardenId());
        dto.setFirstName(warden.getFirstName());
        dto.setLastName(warden.getLastName());
        dto.setEmail(warden.getEmail());
        dto.setPhoneNumber(warden.getPhoneNumber());
        dto.setDateOfBirth(warden.getDateOfBirth());
        dto.setAssignedBlock(warden.getAssignedBlock());
        dto.setQualification(warden.getQualification());
        dto.setAddress(warden.getAddress());
        dto.setCity(warden.getCity());
        dto.setState(warden.getState());
        dto.setStatus(warden.getStatus() != null ? warden.getStatus().name() : null);
        dto.setCreatedAt(warden.getCreatedAt());
        dto.setUpdatedAt(warden.getUpdatedAt());
        dto.setUser(toUserDto(warden.getUser()));
        return dto;
    }

    public List<WardenDto> toWardenDtoList(List<Warden> wardens) {
        if (wardens == null) return Collections.emptyList();
        return wardens.stream().map(this::toWardenDto).collect(Collectors.toList());
    }

    // ── Request → Entity helpers ──────────────────────────────────────────────

    /** Applies a RoomRequest onto a Room entity (create or update). */
    public void applyRoomRequest(RoomRequest req, Room room) {
        room.setRoomNumber(req.getRoomNumber());
        room.setRoomType(Room.RoomType.valueOf(req.getRoomType().toUpperCase()));
        room.setCapacity(req.getCapacity());
        room.setFloor(req.getFloor());
        room.setBlock(req.getBlock());
        room.setMonthlyFee(req.getMonthlyFee());
        room.setDescription(req.getDescription());
        room.setHasAc(req.isHasAc());
        room.setHasAttachedBathroom(req.isHasAttachedBathroom());
        if (req.getStatus() != null) {
            room.setStatus(Room.RoomStatus.valueOf(req.getStatus().toUpperCase()));
        }
    }

    /** Applies a PaymentRequest onto a Payment entity (create). */
    public void applyPaymentRequest(PaymentRequest req, Payment payment) {
        payment.setAmount(req.getAmount());
        payment.setPaymentType(Payment.PaymentType.valueOf(req.getPaymentType().toUpperCase()));
        payment.setDueDate(req.getDueDate());
        payment.setForMonth(req.getForMonth());
        payment.setDescription(req.getDescription());
        if (req.getPaymentMethod() != null) {
            payment.setPaymentMethod(
                    Payment.PaymentMethod.valueOf(req.getPaymentMethod().toUpperCase()));
        }
    }

    /** Applies a MessMenuRequest onto a MessMenu entity (create or update). */
    public void applyMessMenuRequest(MessMenuRequest req, MessMenu menu) {
        menu.setDayOfWeek(req.getDayOfWeek().toUpperCase());
        menu.setMealType(MessMenu.MealType.valueOf(req.getMealType().toUpperCase()));
        menu.setItems(req.getItems());
        menu.setDescription(req.getDescription());
        menu.setSpecial(req.isSpecial());
    }

    /** Applies a StudentUpdateRequest onto a Student entity. */
    public void applyStudentUpdateRequest(StudentUpdateRequest req, Student student) {
        if (req.getFirstName()   != null) student.setFirstName(req.getFirstName());
        if (req.getLastName()    != null) student.setLastName(req.getLastName());
        if (req.getPhoneNumber() != null) student.setPhoneNumber(req.getPhoneNumber());
        if (req.getParentName()  != null) student.setParentName(req.getParentName());
        if (req.getParentPhone() != null) student.setParentPhone(req.getParentPhone());
        if (req.getCourse()      != null) student.setCourse(req.getCourse());
        if (req.getBranch()      != null) student.setBranch(req.getBranch());
        if (req.getYear()        != null) student.setYear(req.getYear());
        if (req.getAddress()     != null) student.setAddress(req.getAddress());
        if (req.getCity()        != null) student.setCity(req.getCity());
        if (req.getState()       != null) student.setState(req.getState());
        if (req.getDateOfBirth() != null && !req.getDateOfBirth().isBlank()) {
            student.setDateOfBirth(LocalDate.parse(req.getDateOfBirth()));
        }
    }
}
