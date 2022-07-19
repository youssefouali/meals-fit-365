package com.mealsfitbackend.controllers;

import com.mealsfitbackend.entities.FoodProduct;
import com.mealsfitbackend.entities.KitchenProduct;
import com.mealsfitbackend.entities.Recipe;
import com.mealsfitbackend.enumerations.FoodType;
import com.mealsfitbackend.services.FoodProductService;
import com.mealsfitbackend.services.KitchenService;
import com.mealsfitbackend.services.RecipeService;
import com.mealsfitbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/kitchen")
public class KitchenController {
    @Autowired
    KitchenService kitchenService;

    @Autowired
    RecipeService recipeService;

    @Autowired
    FoodProductService foodService;

    /*@Autowired*/
    UserService userService;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/removeProduct")
    public @ResponseBody
    String removeProduct(@RequestParam int foodProductId) {
        FoodProduct foodProduct = foodService.getFoodProduct(foodProductId);
        long userId = userService.getCurrentUserId();
        kitchenService.removeKitchenProductByFoodProductId(userId, foodProduct.getId());
        return "success";
    }

    @GetMapping("/addProduct")
    public @ResponseBody String addProduct(@RequestParam int foodProductId) {
        FoodProduct foodProduct = foodService.getFoodProduct(foodProductId);
        KitchenProduct kitchenProduct = new KitchenProduct();
        kitchenProduct.setUser(userService.getCurrentUser());
        kitchenProduct.setFoodProduct(foodProduct);
        kitchenService.addProduct(kitchenProduct);
        return "success";
    }

    @GetMapping("/getUserProducts")
    public @ResponseBody
    List<KitchenProduct> getUserKitchenProducts(){
        List<KitchenProduct> products =  kitchenService.getAllProductsForUser(userService.getCurrentUserId());
        return products;
    }

    @GetMapping("/getFoodTypes")
    public @ResponseBody FoodType[] getFoodTypes(){
        return FoodType.values();
    }

    @GetMapping("/getAvailableRecipes")
    public @ResponseBody List<Recipe> getRecipesForUsersProducts(){
        List<KitchenProduct> kitchenProducts = kitchenService.getAllProductsForUser(userService.getCurrentUserId());
        return recipeService.getRecipesForUserProducts(kitchenProducts);
    }

}
