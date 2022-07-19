package com.mealsfitbackend.services;

import com.mealsfitbackend.dto.ShoppingItemDTO;
import com.mealsfitbackend.entities.*;
import com.mealsfitbackend.enumerations.MealType;
import com.mealsfitbackend.enumerations.UnitType;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface PlanService {

    Plan getCurrentPlan(User user);

    void save(Plan plan);

    Plan getPlanById(int id);

    Meal getMeal(int id);

    Meal getMeal(int planId, LocalDate mealDate, MealType type);

    Plan getPlanByMealId(int mealId);

    List<Plan> getAllUserPlans(long userId);

    List<Plan> getAllUserPlansFrom(long userId, LocalDate date);

    List<Plan> getAllUserPlansUntil(long userId, LocalDate date);

    public void updateShoppingItems(int planId, String ingredientName, boolean isDone, UnitType unitType);

    public Map<String, List<ShoppingItemDTO>> getPreparedShoppingList(int planId);

    void addDishToMeal(Meal meal, Dish dish, int servings);

    void removeDishFromMeal(Meal meal, Dish dish);

    void saveSingleDish(SingleDishProduct singleDish);

    SingleDishProduct getSingleDishProduct(int id);

    void deleteSingleDish(SingleDishProduct singleDishProduct);

    void copyPlanMealsToPlan(Plan planWithMeals, Plan newPlan);
}
