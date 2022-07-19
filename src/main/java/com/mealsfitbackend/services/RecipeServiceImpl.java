package com.mealsfitbackend.services;

import com.mealsfitbackend.Repositories.DishRepository;
import com.mealsfitbackend.Repositories.FoodProductRepository;
import com.mealsfitbackend.Repositories.RecipeRepository;
import com.mealsfitbackend.Repositories.UserRepository;
import com.mealsfitbackend.entities.Ingredient;
import com.mealsfitbackend.entities.KitchenProduct;
import com.mealsfitbackend.entities.Recipe;
import com.mealsfitbackend.entities.User;
import com.mealsfitbackend.enumerations.MealType;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService{

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    FoodProductRepository foodProductRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DishRepository dishRepository;

    //@Autowired
    ModelMapper mapper;

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository, FoodProductRepository foodProductRepository) {
        this.recipeRepository = recipeRepository;
        this.foodProductRepository = foodProductRepository;
    }

    @Override
    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    @Override
    public Recipe findById(int id) {
        Optional<Recipe> result = recipeRepository.findById(id);
        Recipe recipe = null;
        if (result.isPresent()) {
            recipe = result.get();
        } else {
            logger.error("Did not find recipe id - " + id);
            throw new RuntimeException("Did not find recipe id - " + id);
        }
        return recipe;
    }

    @Override
    public void save(Recipe recipe) {
        recipeRepository.save(recipe);
    }

    @Override
    public void deleteById(int id) {
        //using dish repository because of orphanRemovals=true for ingredients in Dish(parent) class
        //so ingredients would be also removed when deleting dish and avoid: Hibernate AssertionFailure: collection owner not associated with session..
        dishRepository.deleteById(id);
    }

    @Override
    public Recipe findByTitle(String title) {
        return recipeRepository.findByTitle(title);
    }

    @Override
    public List<Recipe> getRecipesForUserProducts(List<KitchenProduct> products) {
        List<Recipe> recipes = recipeRepository.findAll();
        List<Recipe> availableRecipes = new ArrayList<Recipe>();
        for (Recipe recipe : recipes) {
            if (areIngredientsInKitchen(recipe.getIngredients(), products)) {
                availableRecipes.add(recipe);
            }
        }
        return availableRecipes;
    }

    private boolean isIngredientInKitchen(Ingredient ingredient, List<KitchenProduct> products) {
        for (KitchenProduct product : products) {
            if (ingredient.getFoodProduct().equals(product.getFoodProduct())) {
                return true;
            }
        }
        return false;
    }

    private boolean areIngredientsInKitchen(List<Ingredient> ingredients, List<KitchenProduct> products) {
        for (Ingredient ingredient : ingredients) {
            if (!isIngredientInKitchen(ingredient, products)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Page<Recipe> getRecipesByPage(int pageId, int pageSize) {
        Pageable pageable = PageRequest.of(pageId, pageSize);
        return recipeRepository.findAll(pageable);
    }

//	@Override
//	public List<String> getNamesLike(String keyword) {
//		List<FoodProduct> products = foodProductRepository.findByNameContaining(keyword);
//		List<String> productNames = new ArrayList<String>();
//		if (!products.isEmpty())
//			for (FoodProduct foodProduct : products) {
//				productNames.add(foodProduct.getName());
//			}
//		return productNames;
//	}

    @Override
    public List<String> getNamesLike(String keyword) {
        return foodProductRepository.findByNameContaining(keyword);
    }

    @Override
    public List<Recipe> findByOwnerIdDesc(long currentUserId) {
        return recipeRepository.findByOwnerIdOrderByIdDesc(currentUserId);
    }

    @Override
    public void makeRecipePublic(int recipeId, User publisher) {
        Recipe recipe = findById(recipeId);
        recipe.setInspected(true);
        createPublicRecipeCopy(recipe, publisher);
        recipeRepository.save(recipe);
    }

    private void createPublicRecipeCopy(Recipe recipe, User publisher) {
        Recipe copyRecipe = new Recipe(recipe);
        copyRecipe.setOwner(publisher);
        copyRecipe.setPublished(true);
        recipeRepository.save(copyRecipe);
    }

    @Override
    public void makeRecipePrivate(int recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId).get();
        recipe.setInspected(true);
        recipe.setPublished(false);
        recipeRepository.save(recipe);
    }

    @Override
    public List<Recipe> getRecipesWaitingForInspection() {
        return recipeRepository.findBySharedAndInspected(true, false);
    }

    @Override
    public List<Recipe> getPublicRecipes() {
        return recipeRepository.findByPublished(true);
    }

    @Override
    public List<Recipe> filterRecipesByMealTypesAndSearchProducts(List<Recipe> recipes,
                                                                  List<MealType> selectedMealtypes, List<String> products) {
        List<Recipe> filteredRecipes;
        if (selectedMealtypes != null)
            filteredRecipes = recipes.stream().filter(
                            recipe -> selectedMealtypes.stream().anyMatch(mealType -> recipe.getMealTypes().contains(mealType)))
                    .collect(Collectors.toList());
        else {
            filteredRecipes = recipes;
        }
        if (products != null) {
            filteredRecipes = filteredRecipes.stream()
                    .filter(recipe -> products.stream()
                            .allMatch(product -> recipe.getIngredients().stream()
                                    .map(ingredient -> ingredient.getFoodProduct().getName().toLowerCase())
                                    .anyMatch(ingredient -> ingredient.contains(product.toLowerCase()))))
                    .collect(Collectors.toList());
        }
        return filteredRecipes;
    }

    @Override
    public Page<Recipe> getPublicRecipes(int pageId, int pageSize) {
        Pageable pageable = PageRequest.of(pageId, pageSize);
        return recipeRepository.findByPublished(true, pageable);
    }

    @Override
    public Page<Recipe> findByOwnerId(long currentUserId, int pageId, int pageSize) {
        Pageable pageable = PageRequest.of(pageId, pageSize);
        return recipeRepository.findByOwnerId(currentUserId, pageable);
    }
}
