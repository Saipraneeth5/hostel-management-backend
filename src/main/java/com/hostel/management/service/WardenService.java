package com.hostel.management.service;

import com.hostel.management.dto.response.WardenDto;
import com.hostel.management.entity.Warden;
import com.hostel.management.exception.GlobalExceptionHandler.ResourceNotFoundException;
import com.hostel.management.mapper.EntityMapper;
import com.hostel.management.repository.WardenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WardenService {

    private final WardenRepository wardenRepository;
    private final EntityMapper     mapper;

    public WardenService(WardenRepository wardenRepository, EntityMapper mapper) {
        this.wardenRepository = wardenRepository;
        this.mapper           = mapper;
    }

    // FIX: @Transactional(readOnly=true) keeps session open so warden.getUser()
    //      (LAZY @OneToOne) can be resolved in the mapper without LazyInitializationException.
    @Transactional(readOnly = true)
    public List<WardenDto> getAllWardens() {
        return mapper.toWardenDtoList(wardenRepository.findAllWithUser());
    }

    @Transactional(readOnly = true)
    public WardenDto getWardenById(Long id) {
        return mapper.toWardenDto(getWardenEntityById(id));
    }

    @Transactional(readOnly = true)
    public WardenDto getWardenByUserId(Long userId) {
        Warden warden = wardenRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Warden profile not found for userId: " + userId));
        return mapper.toWardenDto(warden);
    }

    @Transactional
    public WardenDto updateOwnProfile(Long userId, Warden incoming) {
        Warden existing = wardenRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Warden profile not found for userId: " + userId));
        existing.setFirstName(incoming.getFirstName());
        existing.setLastName(incoming.getLastName());
        existing.setPhoneNumber(incoming.getPhoneNumber());
        existing.setDateOfBirth(incoming.getDateOfBirth());
        existing.setQualification(incoming.getQualification());
        existing.setAddress(incoming.getAddress());
        existing.setCity(incoming.getCity());
        existing.setState(incoming.getState());
        return mapper.toWardenDto(wardenRepository.save(existing));
    }

    @Transactional
    public WardenDto updateWarden(Long id, Warden incoming) {
        Warden existing = getWardenEntityById(id);
        existing.setFirstName(incoming.getFirstName());
        existing.setLastName(incoming.getLastName());
        existing.setPhoneNumber(incoming.getPhoneNumber());
        existing.setDateOfBirth(incoming.getDateOfBirth());
        existing.setQualification(incoming.getQualification());
        existing.setAssignedBlock(incoming.getAssignedBlock());
        existing.setAddress(incoming.getAddress());
        existing.setCity(incoming.getCity());
        existing.setState(incoming.getState());
        existing.setStatus(incoming.getStatus());
        return mapper.toWardenDto(wardenRepository.save(existing));
    }

    @Transactional
    public void deleteWarden(Long id) {
        wardenRepository.delete(getWardenEntityById(id));
    }

    private Warden getWardenEntityById(Long id) {
        return wardenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Warden not found with id: " + id));
    }
}
