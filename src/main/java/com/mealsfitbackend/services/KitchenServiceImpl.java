package com.mealsfitbackend.services;

import com.mealsfitbackend.Repositories.KitchenRepository;
import com.mealsfitbackend.entities.KitchenProduct;
import com.mealsfitbackend.exceptions.UniqueProductConstraintValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class KitchenServiceImpl implements KitchenService{

    @Autowired
    KitchenRepository kitchenRepository;

    @Override
    public List<KitchenProduct> getAllProductsForUser(long userId) {
        return kitchenRepository.findByUserId(userId);
    }

    @Override
    public void addProduct(KitchenProduct product) {
        try {
            kitchenRepository.save(product);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueProductConstraintValidationException(product.getFoodProduct().getName());
        }

    }

    @Override
    public void removeProduct(int id) {
        kitchenRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void removeKitchenProductByFoodProductId(long userId, int id) {
        kitchenRepository.deleteByUserIdAndFoodProductId(userId, id);
    }

}
