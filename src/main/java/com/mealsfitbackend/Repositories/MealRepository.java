package com.mealsfitbackend.Repositories;

import com.mealsfitbackend.entities.Meal;
import com.mealsfitbackend.enumerations.MealType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface MealRepository  extends JpaRepository<Meal, Integer> {
    Meal findByPlanIdAndDateAndMealType(int planId, LocalDate date, MealType mealType);
}
