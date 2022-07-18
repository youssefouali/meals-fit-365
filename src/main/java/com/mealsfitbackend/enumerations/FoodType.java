package com.mealsfitbackend.enumerations;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum FoodType {
    VEGETABLE("VEGETABLE"),
    FRUIT("FRUIT"),
    GRAINS_BEANS_NUTS("GRAINS_BEANS_NUTS"),
    MEAT_POULTRY("MEAT_POULTRY"),
    FISH_SEAFOOD("FISH_SEAFOOD"),
    DAIRY("DAIRY"),
    OTHER("OTHER");

    public final String label;


    private FoodType(String label) {
        this.label = label;
    }

    public String getValue() {
        return this.name();
    }

    public String getLabel() {
        return label;
    }
}
