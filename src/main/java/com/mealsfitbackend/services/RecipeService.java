package com.mealsfitbackend.services;

import com.mealsfitbackend.entities.KitchenProduct;
import com.mealsfitbackend.entities.Recipe;
import com.mealsfitbackend.entities.User;
import com.mealsfitbackend.enumerations.MealType;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RecipeService {

    public List<Recipe> findAll();

    public Recipe findById(int id);

    public void save(Recipe recipe);

    public void deleteById(int id);

    public Recipe findByTitle(String title);

    public List<Recipe> getRecipesForUserProducts(List<KitchenProduct> products);

    public Page<Recipe> getRecipesByPage(int pageId, int pageSize);

    public List<String> getNamesLike(String keyword);

    public List<Recipe> findByOwnerIdDesc(long currentUserId);

    public List<Recipe> getRecipesWaitingForInspection();

    public List<Recipe> getPublicRecipes();

    public void makeRecipePublic(int recipeId, User publisher);

    public void makeRecipePrivate(int recipeId);

    public List<Recipe> filterRecipesByMealTypesAndSearchProducts(List<Recipe> recipes,
                                                                  List<MealType> selectedMealtypes, List<String> products);

    public Page<Recipe> getPublicRecipes(int pageId, int pageSize);

    public Page<Recipe> findByOwnerId(long currentUserId, int pageId, int pageSize);
}
