package com.mealsfitbackend.exceptions;

public class UniqueProductConstraintValidationException extends RuntimeException {

    public UniqueProductConstraintValidationException(String name) {
        super("There is already a product with the name : "+ name);
    }

}
