package com.hostel.management.dto.request;

/**
 * Fields a student is allowed to update themselves.
 * email, username, studentId, roomId are intentionally absent.
 */
public class StudentUpdateRequest {

    private String  firstName;
    private String  lastName;
    private String  phoneNumber;
    private String  parentName;
    private String  parentPhone;
    private String  dateOfBirth;
    private String  course;
    private String  branch;
    private Integer year;
    private String  address;
    private String  city;
    private String  state;

    public StudentUpdateRequest() {}

    public String getFirstName()             { return firstName; }
    public void setFirstName(String v)       { this.firstName = v; }
    public String getLastName()              { return lastName; }
    public void setLastName(String v)        { this.lastName = v; }
    public String getPhoneNumber()           { return phoneNumber; }
    public void setPhoneNumber(String v)     { this.phoneNumber = v; }
    public String getParentName()            { return parentName; }
    public void setParentName(String v)      { this.parentName = v; }
    public String getParentPhone()           { return parentPhone; }
    public void setParentPhone(String v)     { this.parentPhone = v; }
    public String getDateOfBirth()           { return dateOfBirth; }
    public void setDateOfBirth(String v)     { this.dateOfBirth = v; }
    public String getCourse()                { return course; }
    public void setCourse(String v)          { this.course = v; }
    public String getBranch()                { return branch; }
    public void setBranch(String v)          { this.branch = v; }
    public Integer getYear()                 { return year; }
    public void setYear(Integer v)           { this.year = v; }
    public String getAddress()               { return address; }
    public void setAddress(String v)         { this.address = v; }
    public String getCity()                  { return city; }
    public void setCity(String v)            { this.city = v; }
    public String getState()                 { return state; }
    public void setState(String v)           { this.state = v; }
}
