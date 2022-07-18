package com.mealsfitbackend.entities;

import com.mealsfitbackend.enumerations.UnitType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "nutrition")
public class Nutrition {

    // All nutrition is for 100g of product

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    //	g/cm^3=g/ml
    @Column(name = "density")
    private float density;

    @Column(name = "kcal")
    private float kcal;

    @Column(name = "protein")
    private float protein;

    @Column(name = "carbs")
    private float carbs;

    @Column(name = "fat")
    private float fat;

    public Nutrition(float kcal, float protein, float carbs, float fat) {
        this.kcal = kcal;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
    }

    public Nutrition getNutritionPerUnitType(UnitType unitType) {
        float conversionCoeff = 1;
        switch (unitType) {
            case GRAM:
                conversionCoeff = conversionCoeff / 100;
                break;
            case KILOGRAM:
                conversionCoeff = (conversionCoeff / 100) * 1000;
                break;
            case MILILITRE:
                conversionCoeff = (conversionCoeff / 100) * density;
                break;
            case LITRE:
                conversionCoeff = (conversionCoeff / 100) * density * 1000;
                break;
            case CUP:
                conversionCoeff = (conversionCoeff / 100) * density * 250;
                break;
            case TABLE_SPOON:
                conversionCoeff = (conversionCoeff / 100) * density * 15;
                break;
            case TEA_SPOON:
                conversionCoeff = (conversionCoeff / 100) * density * 5;
                break;
            default:
                break;
        }
        Nutrition nutrition = new Nutrition();
        nutrition.setKcal(this.getKcal() * conversionCoeff);
        nutrition.setProtein(this.getProtein() * conversionCoeff);
        nutrition.setCarbs(this.getCarbs() * conversionCoeff);
        nutrition.setFat(this.getFat() * conversionCoeff);
        return nutrition;
    }

    public static Nutrition sumNutritions(List<Nutrition> nutritions) {
        float kcal = 0, carbs = 0, fat = 0, protein = 0;
        for (Nutrition nutrition : nutritions) {
            kcal = kcal + nutrition.getKcal();
            carbs = carbs + nutrition.getCarbs();
            fat = fat + nutrition.getFat();
            protein = protein + nutrition.getProtein();
        }
        return new Nutrition(kcal, protein, carbs, fat);
    }

    public static Nutrition multiplyNutritionsByFloat(Nutrition nutrition, float arg) {
        nutrition.setKcal(nutrition.getKcal() * arg);
        nutrition.setCarbs(nutrition.getCarbs() * arg);
        nutrition.setFat(nutrition.getFat() * arg);
        nutrition.setProtein(nutrition.getProtein() * arg);
        return nutrition;
    }

    public static Nutrition divideNutritionsByLong(Nutrition nutrition, long arg) {
        nutrition.setKcal(nutrition.getKcal() / arg);
        nutrition.setCarbs(nutrition.getCarbs() / arg);
        nutrition.setFat(nutrition.getFat() / arg);
        nutrition.setProtein(nutrition.getProtein() / arg);
        return nutrition;
    }



}
