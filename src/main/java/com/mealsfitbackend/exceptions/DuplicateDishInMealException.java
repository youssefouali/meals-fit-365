package com.mealsfitbackend.exceptions;

import com.mealsfitbackend.entities.Dish;
import com.mealsfitbackend.entities.Meal;

public class DuplicateDishInMealException extends RuntimeException{

    public DuplicateDishInMealException(Dish dish, Meal meal) {
        super("Recipe or product name "+ dish.getTitle() +" is already assigned to: "+meal.getDate()+" " + meal.getMealType().getLabel());
    }
}