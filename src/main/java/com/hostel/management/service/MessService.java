package com.hostel.management.service;

import com.hostel.management.dto.request.MessMenuRequest;
import com.hostel.management.dto.response.MessMenuDto;
import com.hostel.management.entity.MessMenu;
import com.hostel.management.exception.GlobalExceptionHandler.ResourceNotFoundException;
import com.hostel.management.mapper.EntityMapper;
import com.hostel.management.repository.MessRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MessService {

    private final MessRepository messRepository;
    private final EntityMapper   mapper;

    public MessService(MessRepository messRepository, EntityMapper mapper) {
        this.messRepository = messRepository;
        this.mapper         = mapper;
    }

    @Transactional(readOnly = true)
    public List<MessMenuDto> getFullMenu() {
        return mapper.toMessMenuDtoList(messRepository.findAll());
    }

    @Transactional(readOnly = true)
    public List<MessMenuDto> getMenuByDay(String day) {
        return mapper.toMessMenuDtoList(
                messRepository.findByDayOfWeekOrderByMealType(day.toUpperCase()));
    }

    @Transactional(readOnly = true)
    public Map<String, List<MessMenuDto>> getWeeklyMenu() {
        return messRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        MessMenu::getDayOfWeek,
                        Collectors.mapping(mapper::toMessMenuDto, Collectors.toList())));
    }

    @Transactional(readOnly = true)
    public List<MessMenuDto> getTodayMenu() {
        DayOfWeek today = LocalDate.now().getDayOfWeek();
        return mapper.toMessMenuDtoList(
                messRepository.findByDayOfWeekOrderByMealType(today.name()));
    }

    @Transactional(readOnly = true)
    public MessMenuDto getMenuById(Long id) {
        return mapper.toMessMenuDto(getMenuEntityById(id));
    }

    @Transactional(readOnly = true)
    public List<MessMenuDto> getMenuByMealType(String mealType) {
        return mapper.toMessMenuDtoList(
                messRepository.findByMealType(
                        MessMenu.MealType.valueOf(mealType.toUpperCase())));
    }

    @Transactional
    public MessMenuDto createMenuItem(MessMenuRequest request) {
        MessMenu menu = new MessMenu();
        mapper.applyMessMenuRequest(request, menu);
        return mapper.toMessMenuDto(messRepository.save(menu));
    }

    @Transactional
    public MessMenuDto updateMenuItem(Long id, MessMenuRequest request) {
        MessMenu existing = getMenuEntityById(id);
        mapper.applyMessMenuRequest(request, existing);
        return mapper.toMessMenuDto(messRepository.save(existing));
    }

    @Transactional
    public void deleteMenuItem(Long id) {
        messRepository.delete(getMenuEntityById(id));
    }

    private MessMenu getMenuEntityById(Long id) {
        return messRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Menu item not found with id: " + id));
    }
}
