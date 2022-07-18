package com.mealsfitbackend.Repositories;

import com.mealsfitbackend.entities.SingleDishProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SingleDishProductRepository extends JpaRepository<SingleDishProduct, Integer> {
}
