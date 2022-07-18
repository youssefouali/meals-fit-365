package com.mealsfitbackend.dto;

import com.mealsfitbackend.entities.FoodProduct;
import com.mealsfitbackend.enumerations.UnitType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IngredientDTO {

    private int id;

    private float ammount;

    private UnitType unitType;

    private FoodProduct foodProduct;

    private int foodProductId;
}
