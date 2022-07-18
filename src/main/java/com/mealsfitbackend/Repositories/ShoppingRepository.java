package com.mealsfitbackend.Repositories;

import com.mealsfitbackend.entities.ShoppingItem;
import com.mealsfitbackend.enumerations.UnitType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoppingRepository extends JpaRepository<ShoppingItem, Integer> {
    List<ShoppingItem> findAllByPlanId(int planId);

    List<ShoppingItem> findAllByPlanIdAndIngredientFoodProductNameAndDoneAndIngredientUnitType(int planId,
                                                                                               String foodProductName, boolean isDone, UnitType unitType);
}
