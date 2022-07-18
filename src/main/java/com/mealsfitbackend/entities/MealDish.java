package com.mealsfitbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "meal_dish")
public class MealDish implements Comparable<MealDish> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "meal_id")
    @JsonIgnore
    private Meal meal;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "dish_id")
    @JsonIgnore
    private Dish dish;

    @Column(name = "servings")
    private int servings;

    public MealDish(Meal meal, Dish dish, int servings) {
        this.meal = meal;
        this.dish = dish;
        this.servings = servings;
    }

    public MealDish(Meal meal, Dish dish) {
        this.meal = meal;
        this.dish = dish;
    }

    public MealDish(MealDish mealDish, Meal meal) {
        this.meal = meal;
        this.servings = mealDish.getServings();
        if (mealDish.getDish().getClass().equals(Recipe.class))
            this.dish = mealDish.getDish();
        else if (mealDish.getDish().getClass().equals(SingleDishProduct.class))
            this.dish = new SingleDishProduct(mealDish.getDish());
    }

    @Override
    public int compareTo(MealDish o) {
        return Integer.compare(this.getId(), o.getId());
    }

    public Nutrition getNutritionForMealDish() {
        //ratio between servings for meal dish and recipe servings
        float ratio =  (float)this.getServings() / (float)this.getDish().getServings();
        return Nutrition.multiplyNutritionsByFloat(getDish().getNutritionForDish(), ratio);
    }

}
