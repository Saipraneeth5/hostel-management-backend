package com.hostel.management.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class WardenDto {

    private Long          id;
    private String        wardenId;
    private String        firstName;
    private String        lastName;
    private String        email;
    private String        phoneNumber;
    private LocalDate     dateOfBirth;
    private String        assignedBlock;
    private String        qualification;
    private String        address;
    private String        city;
    private String        state;
    private String        status;
    private UserDto       user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public WardenDto() {}

    public Long getId()                       { return id; }
    public void setId(Long id)                { this.id = id; }
    public String getWardenId()               { return wardenId; }
    public void setWardenId(String v)         { this.wardenId = v; }
    public String getFirstName()              { return firstName; }
    public void setFirstName(String v)        { this.firstName = v; }
    public String getLastName()               { return lastName; }
    public void setLastName(String v)         { this.lastName = v; }
    public String getEmail()                  { return email; }
    public void setEmail(String v)            { this.email = v; }
    public String getPhoneNumber()            { return phoneNumber; }
    public void setPhoneNumber(String v)      { this.phoneNumber = v; }
    public LocalDate getDateOfBirth()         { return dateOfBirth; }
    public void setDateOfBirth(LocalDate v)   { this.dateOfBirth = v; }
    public String getAssignedBlock()          { return assignedBlock; }
    public void setAssignedBlock(String v)    { this.assignedBlock = v; }
    public String getQualification()          { return qualification; }
    public void setQualification(String v)    { this.qualification = v; }
    public String getAddress()                { return address; }
    public void setAddress(String v)          { this.address = v; }
    public String getCity()                   { return city; }
    public void setCity(String v)             { this.city = v; }
    public String getState()                  { return state; }
    public void setState(String v)            { this.state = v; }
    public String getStatus()                 { return status; }
    public void setStatus(String v)           { this.status = v; }
    public UserDto getUser()                  { return user; }
    public void setUser(UserDto v)            { this.user = v; }
    public LocalDateTime getCreatedAt()       { return createdAt; }
    public void setCreatedAt(LocalDateTime v) { this.createdAt = v; }
    public LocalDateTime getUpdatedAt()       { return updatedAt; }
    public void setUpdatedAt(LocalDateTime v) { this.updatedAt = v; }
}
