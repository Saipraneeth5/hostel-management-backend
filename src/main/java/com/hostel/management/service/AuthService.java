package com.hostel.management.service;

import com.hostel.management.dto.LoginRequest;
import com.hostel.management.dto.RegisterRequest;
import com.hostel.management.entity.Student;
import com.hostel.management.entity.User;
import com.hostel.management.repository.StudentRepository;
import com.hostel.management.repository.UserRepository;
import com.hostel.management.util.JwtUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final ApplicationContext applicationContext;

    public AuthService(UserRepository userRepository,
                       StudentRepository studentRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil,
                       ApplicationContext applicationContext) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.applicationContext = applicationContext;
    }

    // Lazy-fetch to avoid circular reference: SecurityConfig → AuthService → PasswordEncoder → SecurityConfig
    private AuthenticationManager getAuthenticationManager() {
        return applicationContext.getBean(AuthenticationManager.class);
    }

    @Transactional
    public Map<String, Object> login(LoginRequest request) {
        getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().name());
        claims.put("email", user.getEmail());
        claims.put("userId", user.getId());

        String token = jwtUtil.generateToken(user, claims);

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("username", user.getUsername());
        response.put("email", user.getEmail());
        response.put("role", user.getRole().name());
        response.put("userId", user.getId());

        if (user.getRole() == User.Role.STUDENT) {
            studentRepository.findByUserId(user.getId()).ifPresent(student -> {
                response.put("studentId", student.getId());
                response.put("studentCode", student.getStudentId());
                response.put("fullName", student.getFullName());
            });
        }

        return response;
    }

    @Transactional
    public Map<String, Object> register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User.Role role = request.getRole() != null ? request.getRole() : User.Role.STUDENT;

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();

        user = userRepository.save(user);

        if (role == User.Role.STUDENT) {
            String generatedStudentId = request.getStudentId() != null
                    ? request.getStudentId()
                    : "STU" + System.currentTimeMillis();

            Student student = Student.builder()
                    .studentId(generatedStudentId)
                    .firstName(request.getFirstName() != null ? request.getFirstName() : request.getUsername())
                    .lastName(request.getLastName() != null ? request.getLastName() : "")
                    .email(request.getEmail())
                    .phoneNumber(request.getPhoneNumber())
                    .course(request.getCourse())
                    .branch(request.getBranch())
                    .year(request.getYear())
                    .admissionDate(LocalDate.now())
                    .user(user)
                    .build();

            studentRepository.save(student);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Registration successful");
        response.put("username", user.getUsername());
        response.put("role", user.getRole().name());
        return response;
    }

    public Map<String, Object> validateToken(String token) {
        String username = jwtUtil.extractUsername(token);
        UserDetails userDetails = applicationContext
                .getBean(CustomUserDetailsService.class)
                .loadUserByUsername(username);
        boolean valid = jwtUtil.validateToken(token, userDetails);
        Map<String, Object> response = new HashMap<>();
        response.put("valid", valid);
        response.put("username", username);
        return response;
    }
}
