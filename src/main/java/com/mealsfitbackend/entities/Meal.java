package com.mealsfitbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mealsfitbackend.enumerations.MealType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "meal")
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<MealDish> mealDishes;

    @Column(name = "meal_type")
    @Enumerated(EnumType.ORDINAL)
    private MealType mealType;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    @JsonIgnore
    private Plan plan;

    public Meal(Meal meal) {
        this.mealDishes = copyMealDishes(meal.getMealDishes());
        this.mealType = meal.mealType;

    }

    public List<MealDish> copyMealDishes(List<MealDish> mealDishes) {
        List<MealDish> newMealDishes = new ArrayList<MealDish>();
        for (MealDish mealDish : mealDishes) {
            newMealDishes.add(new MealDish(mealDish, this));
        }
        return newMealDishes;
    }
    public Nutrition getNutritionForMeal() {
        List<Nutrition> nutritions = mealDishes.stream().map(i -> i.getNutritionForMealDish())
                .collect(Collectors.toList());
        return Nutrition.sumNutritions(nutritions);
    }

    public void addDish(Dish dish, int servings) {
        MealDish mealDish = new MealDish(this, dish, servings);
        mealDishes.add(mealDish);
    }

    public void removeDish(Dish dish) {
        MealDish mealDish = findMealDish(dish);
        mealDishes.remove(mealDish);
    }

    public MealDish findMealDish(Dish dish) {
        for (MealDish mealDish : mealDishes) {
            if (mealDish.getDish().equals(dish)) {
                return mealDish;
            }
        }
        return null;
    }

    public boolean hasDish(Dish dish) {
        if (findMealDish(dish) != null)
            return true;
        else
            return false;
    }
}
