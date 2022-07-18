package com.mealsfitbackend.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collections;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "single_dish_product")
public class SingleDishProduct extends Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Transient
    private final int servings = 1;

    public SingleDishProduct(Dish singleDish) {
        this.setId(0);
        this.setIngredients(cloneIngredients(singleDish.getIngredients()));
    }

    @Override
    public String getTitle() {
        return this.getIngredients().get(0).getFoodProduct().getName();
    }

    @Override
    public int getServings() {
        return servings;
    }

    public void setIngredient(Ingredient ingredient) {
        this.setIngredients(Collections.singletonList(ingredient));
    }

}
