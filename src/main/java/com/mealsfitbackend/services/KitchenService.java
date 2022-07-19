package com.mealsfitbackend.services;

import com.mealsfitbackend.entities.KitchenProduct;

import java.util.List;

public interface KitchenService {


    public List<KitchenProduct> getAllProductsForUser(long id);

    public void addProduct(KitchenProduct product);

    public void removeProduct(int id);

    public void removeKitchenProductByFoodProductId(long userId, int id);
}
