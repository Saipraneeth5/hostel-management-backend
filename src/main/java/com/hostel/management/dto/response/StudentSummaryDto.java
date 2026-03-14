package com.hostel.management.dto.response;

/**
 * Slim student projection embedded inside RoomDto.
 * Does NOT contain a room field — that would recreate the circular reference.
 */
public class StudentSummaryDto {

    private Long   id;
    private String studentId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String course;
    private String branch;
    private Integer year;
    private String status;

    public StudentSummaryDto() {}

    public Long getId()                  { return id; }
    public void setId(Long id)           { this.id = id; }
    public String getStudentId()         { return studentId; }
    public void setStudentId(String v)   { this.studentId = v; }
    public String getFirstName()         { return firstName; }
    public void setFirstName(String v)   { this.firstName = v; }
    public String getLastName()          { return lastName; }
    public void setLastName(String v)    { this.lastName = v; }
    public String getEmail()             { return email; }
    public void setEmail(String v)       { this.email = v; }
    public String getPhoneNumber()       { return phoneNumber; }
    public void setPhoneNumber(String v) { this.phoneNumber = v; }
    public String getCourse()            { return course; }
    public void setCourse(String v)      { this.course = v; }
    public String getBranch()            { return branch; }
    public void setBranch(String v)      { this.branch = v; }
    public Integer getYear()             { return year; }
    public void setYear(Integer v)       { this.year = v; }
    public String getStatus()            { return status; }
    public void setStatus(String v)      { this.status = v; }
}
