package com.mealsfitbackend.entities;

import com.mealsfitbackend.enumerations.FoodType;
import com.mealsfitbackend.enumerations.UnitType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "food_product")
public class FoodProduct {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "food_type")
    @Enumerated(EnumType.ORDINAL)
    private FoodType foodType;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "id")
    private Nutrition nutrition;

    public FoodProduct(String name, FoodType foodType, Nutrition nutrition) {
        this.name = name;
        this.foodType = foodType;
        this.nutrition = nutrition;
    }

    public Nutrition getNutritionPerUnitType(UnitType unitType) {
        return this.getNutrition().getNutritionPerUnitType(unitType);
    }
}
