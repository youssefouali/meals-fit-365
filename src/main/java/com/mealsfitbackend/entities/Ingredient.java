package com.mealsfitbackend.entities;

import com.mealsfitbackend.enumerations.UnitType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ingredient")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ammount")
    private float ammount;

    @Column(name="unit_type")
    @Enumerated(EnumType.ORDINAL)
    private UnitType unitType;

    @ManyToOne
    @JoinColumn(name = "food_product_id")
    private FoodProduct foodProduct;

    public Ingredient(Ingredient ingredient) {
        this.ammount = ingredient.ammount;
        this.unitType = ingredient.unitType;
        this.foodProduct = ingredient.foodProduct;
    }

    public Nutrition getNutritionForIngredient() {
        return Nutrition.multiplyNutritionsByFloat(foodProduct.getNutritionPerUnitType(unitType), ammount);
    }
}
