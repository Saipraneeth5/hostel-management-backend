package com.hostel.management.dto.response;

/**
 * Safe user summary exposed in API responses.
 * Password and Spring Security internals are deliberately excluded.
 */
public class UserDto {

    private Long id;
    private String username;
    private String email;
    private String role;
    private boolean isActive;

    public UserDto() {}

    public UserDto(Long id, String username, String email, String role, boolean isActive) {
        this.id       = id;
        this.username = username;
        this.email    = email;
        this.role     = role;
        this.isActive = isActive;
    }

    public Long getId()          { return id; }
    public void setId(Long id)   { this.id = id; }
    public String getUsername()  { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail()     { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRole()      { return role; }
    public void setRole(String role) { this.role = role; }
    public boolean isActive()    { return isActive; }
    public void setActive(boolean active) { this.isActive = active; }
}
