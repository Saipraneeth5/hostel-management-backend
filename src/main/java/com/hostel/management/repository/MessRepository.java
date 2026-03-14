package com.hostel.management.repository;

import com.hostel.management.entity.MessMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessRepository extends JpaRepository<MessMenu, Long> {
    List<MessMenu> findByDayOfWeek(String dayOfWeek);
    List<MessMenu> findByMealType(MessMenu.MealType mealType);
    List<MessMenu> findByDayOfWeekAndMealType(String dayOfWeek, MessMenu.MealType mealType);
    List<MessMenu> findByDayOfWeekOrderByMealType(String dayOfWeek);
}
