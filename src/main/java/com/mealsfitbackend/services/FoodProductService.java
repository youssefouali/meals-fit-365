package com.mealsfitbackend.services;

import com.mealsfitbackend.entities.FoodProduct;
import com.mealsfitbackend.enumerations.FoodType;

import java.util.List;

public interface FoodProductService {

    public List<FoodProduct> getFoodProductsByType(FoodType foodType);

    public List<FoodProduct> getFoodProducts();

    public FoodProduct getFoodProduct(int foodProductId);

    public FoodProduct getFoodProduct(String name);

    public void addFoodProduct(FoodProduct foodProduct);

    public void deleteFoodProduct(int id);
}
