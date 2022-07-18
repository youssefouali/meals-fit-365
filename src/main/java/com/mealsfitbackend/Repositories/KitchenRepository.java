package com.mealsfitbackend.Repositories;

import com.mealsfitbackend.entities.KitchenProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KitchenRepository extends JpaRepository<KitchenProduct, Integer> {

    List<KitchenProduct> findByUserId(long userId);

    void deleteByUserIdAndFoodProductId(long userId, int id);
}
