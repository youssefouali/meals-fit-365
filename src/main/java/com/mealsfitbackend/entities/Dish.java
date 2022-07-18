package com.mealsfitbackend.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "dish")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "dish_id", nullable = false)
    private List<Ingredient> ingredients;

    public Nutrition getNutritionForDish() {
        List<Nutrition> nutritions = ingredients.stream().map(i -> i.getNutritionForIngredient())
                .collect(Collectors.toList());
        return Nutrition.sumNutritions(nutritions);
    }

    public abstract String getTitle();

    public abstract int getServings();

    public List<Ingredient> cloneIngredients(List<Ingredient> ingredients) {
        List<Ingredient> copyOfIngredients = new ArrayList<Ingredient>();
        for (Ingredient ingredient : ingredients) {
            copyOfIngredients.add(new Ingredient(ingredient));
        }
        return copyOfIngredients;
    }
}
