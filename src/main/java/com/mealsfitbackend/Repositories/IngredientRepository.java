package com.mealsfitbackend.Repositories;

import com.mealsfitbackend.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
}
