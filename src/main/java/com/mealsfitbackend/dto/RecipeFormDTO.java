package com.mealsfitbackend.dto;


import com.mealsfitbackend.entities.Nutrition;
import com.mealsfitbackend.entities.Preparation;
import com.mealsfitbackend.enumerations.MealType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RecipeFormDTO {

    private int id;

    @NotNull(message = "Enter the name of the recipe")
    @Size(min = 1, message = "Enter the name of the recipe")
    private String title;

    @NotNull(message = "The recipe must contain at least one ingredient")
    private List<IngredientDTO> ingredients;

    @NotNull(message = "The recipe must include preparation instructions")
    private List<Preparation> preparations;

    @NotNull(message = "\n" +
            "Choose at least one type of dish")
    @Size(min = 1, message = "\n" +
            "Choose at least one type of dish")
    private List<MealType> mealTypes;

    private String image;

    private MultipartFile imageFile;

    private String owner;

    private String author;

    private String description;

    private boolean shared;

    private boolean inspected;

    private boolean published;

    private int servings;

    private Nutrition nutritionForDish;
}
