package com.mealsfitbackend.Repositories;

import com.mealsfitbackend.entities.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepository extends JpaRepository<Dish, Integer> {
}
