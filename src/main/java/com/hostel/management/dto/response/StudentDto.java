package com.hostel.management.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Full student response.
 * room field uses RoomSummaryDto (no students list inside)
 * which breaks the Student → Room → Student cycle.
 * user field uses UserDto (no password).
 */
public class StudentDto {

    private Long           id;
    private String         studentId;
    private String         firstName;
    private String         lastName;
    private String         email;
    private String         phoneNumber;
    private String         parentPhone;
    private String         parentName;
    private LocalDate      dateOfBirth;
    private String         course;
    private String         branch;
    private Integer        year;
    private LocalDate      admissionDate;
    private LocalDate      checkOutDate;
    private String         address;
    private String         city;
    private String         state;
    private String         status;
    private String         profilePicture;
    private RoomSummaryDto room;
    private UserDto        user;
    private LocalDateTime  createdAt;
    private LocalDateTime  updatedAt;

    public StudentDto() {}

    public Long getId()                       { return id; }
    public void setId(Long id)                { this.id = id; }
    public String getStudentId()              { return studentId; }
    public void setStudentId(String v)        { this.studentId = v; }
    public String getFirstName()              { return firstName; }
    public void setFirstName(String v)        { this.firstName = v; }
    public String getLastName()               { return lastName; }
    public void setLastName(String v)         { this.lastName = v; }
    public String getEmail()                  { return email; }
    public void setEmail(String v)            { this.email = v; }
    public String getPhoneNumber()            { return phoneNumber; }
    public void setPhoneNumber(String v)      { this.phoneNumber = v; }
    public String getParentPhone()            { return parentPhone; }
    public void setParentPhone(String v)      { this.parentPhone = v; }
    public String getParentName()             { return parentName; }
    public void setParentName(String v)       { this.parentName = v; }
    public LocalDate getDateOfBirth()         { return dateOfBirth; }
    public void setDateOfBirth(LocalDate v)   { this.dateOfBirth = v; }
    public String getCourse()                 { return course; }
    public void setCourse(String v)           { this.course = v; }
    public String getBranch()                 { return branch; }
    public void setBranch(String v)           { this.branch = v; }
    public Integer getYear()                  { return year; }
    public void setYear(Integer v)            { this.year = v; }
    public LocalDate getAdmissionDate()       { return admissionDate; }
    public void setAdmissionDate(LocalDate v) { this.admissionDate = v; }
    public LocalDate getCheckOutDate()        { return checkOutDate; }
    public void setCheckOutDate(LocalDate v)  { this.checkOutDate = v; }
    public String getAddress()                { return address; }
    public void setAddress(String v)          { this.address = v; }
    public String getCity()                   { return city; }
    public void setCity(String v)             { this.city = v; }
    public String getState()                  { return state; }
    public void setState(String v)            { this.state = v; }
    public String getStatus()                 { return status; }
    public void setStatus(String v)           { this.status = v; }
    public String getProfilePicture()         { return profilePicture; }
    public void setProfilePicture(String v)   { this.profilePicture = v; }
    public RoomSummaryDto getRoom()           { return room; }
    public void setRoom(RoomSummaryDto v)     { this.room = v; }
    public UserDto getUser()                  { return user; }
    public void setUser(UserDto v)            { this.user = v; }
    public LocalDateTime getCreatedAt()       { return createdAt; }
    public void setCreatedAt(LocalDateTime v) { this.createdAt = v; }
    public LocalDateTime getUpdatedAt()       { return updatedAt; }
    public void setUpdatedAt(LocalDateTime v) { this.updatedAt = v; }
}
