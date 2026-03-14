package com.hostel.management.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id", unique = true, nullable = false)
    private String studentId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "parent_phone")
    private String parentPhone;

    @Column(name = "parent_name")
    private String parentName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    private String course;
    private String branch;
    private Integer year;

    @Column(name = "admission_date")
    private LocalDate admissionDate;

    @Column(name = "check_out_date")
    private LocalDate checkOutDate;

    private String address;
    private String city;
    private String state;

    @Enumerated(EnumType.STRING)
    private StudentStatus status = StudentStatus.ACTIVE;

    @Column(name = "profile_picture")
    private String profilePicture;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Complaint> complaints;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Payment> payments;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public enum StudentStatus { ACTIVE, INACTIVE, CHECKED_OUT }

    public Student() {}

    @PrePersist
    protected void onCreate() { createdAt = LocalDateTime.now(); updatedAt = LocalDateTime.now(); }

    @PreUpdate
    protected void onUpdate() { updatedAt = LocalDateTime.now(); }

    public String getFullName() { return firstName + " " + lastName; }

    // ── Builder ──────────────────────────────────────────────────────────────
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String studentId, firstName, lastName, email, phoneNumber;
        private String parentPhone, parentName, course, branch, address, city, state, profilePicture;
        private Integer year;
        private LocalDate dateOfBirth, admissionDate, checkOutDate;
        private StudentStatus status = StudentStatus.ACTIVE;
        private User user;
        private Room room;

        public Builder id(Long v) { this.id = v; return this; }
        public Builder studentId(String v) { this.studentId = v; return this; }
        public Builder firstName(String v) { this.firstName = v; return this; }
        public Builder lastName(String v) { this.lastName = v; return this; }
        public Builder email(String v) { this.email = v; return this; }
        public Builder phoneNumber(String v) { this.phoneNumber = v; return this; }
        public Builder parentPhone(String v) { this.parentPhone = v; return this; }
        public Builder parentName(String v) { this.parentName = v; return this; }
        public Builder dateOfBirth(LocalDate v) { this.dateOfBirth = v; return this; }
        public Builder course(String v) { this.course = v; return this; }
        public Builder branch(String v) { this.branch = v; return this; }
        public Builder year(Integer v) { this.year = v; return this; }
        public Builder admissionDate(LocalDate v) { this.admissionDate = v; return this; }
        public Builder checkOutDate(LocalDate v) { this.checkOutDate = v; return this; }
        public Builder address(String v) { this.address = v; return this; }
        public Builder city(String v) { this.city = v; return this; }
        public Builder state(String v) { this.state = v; return this; }
        public Builder status(StudentStatus v) { this.status = v; return this; }
        public Builder profilePicture(String v) { this.profilePicture = v; return this; }
        public Builder user(User v) { this.user = v; return this; }
        public Builder room(Room v) { this.room = v; return this; }

        public Student build() {
            Student s = new Student();
            s.id = id; s.studentId = studentId; s.firstName = firstName; s.lastName = lastName;
            s.email = email; s.phoneNumber = phoneNumber; s.parentPhone = parentPhone;
            s.parentName = parentName; s.dateOfBirth = dateOfBirth; s.course = course;
            s.branch = branch; s.year = year; s.admissionDate = admissionDate;
            s.checkOutDate = checkOutDate; s.address = address; s.city = city; s.state = state;
            s.status = status; s.profilePicture = profilePicture; s.user = user; s.room = room;
            return s;
        }
    }

    // ── Getters & Setters ────────────────────────────────────────────────────
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getParentPhone() { return parentPhone; }
    public void setParentPhone(String parentPhone) { this.parentPhone = parentPhone; }
    public String getParentName() { return parentName; }
    public void setParentName(String parentName) { this.parentName = parentName; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }
    public String getBranch() { return branch; }
    public void setBranch(String branch) { this.branch = branch; }
    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
    public LocalDate getAdmissionDate() { return admissionDate; }
    public void setAdmissionDate(LocalDate admissionDate) { this.admissionDate = admissionDate; }
    public LocalDate getCheckOutDate() { return checkOutDate; }
    public void setCheckOutDate(LocalDate checkOutDate) { this.checkOutDate = checkOutDate; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    public StudentStatus getStatus() { return status; }
    public void setStatus(StudentStatus status) { this.status = status; }
    public String getProfilePicture() { return profilePicture; }
    public void setProfilePicture(String profilePicture) { this.profilePicture = profilePicture; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Room getRoom() { return room; }
    public void setRoom(Room room) { this.room = room; }
    public List<Complaint> getComplaints() { return complaints; }
    public void setComplaints(List<Complaint> complaints) { this.complaints = complaints; }
    public List<Payment> getPayments() { return payments; }
    public void setPayments(List<Payment> payments) { this.payments = payments; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
