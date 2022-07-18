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
public class RecipeDTO {

    private int id;

    @NotNull(message = "Įveskite recepto pavadinimą")
    @Size(min = 1, message = "Įveskite recepto pavadinimą")
    private String title;

    @NotNull(message = "Recepte turi būti bent vienas ingredientas")
    private List<IngredientDTO> ingredients;

    @NotNull(message = "Recepte turi būti paruošimo instrukcija")
    private List<Preparation> preparations;

    @NotNull(message = "Pasirinkite bent vieną patiekalo tipą")
    @Size(min = 1, message = "Pasirinkite bent vieną patiekalo tipą")
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
