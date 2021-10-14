package com.cuongnghiem.springbootrecipe.repositories;

import com.cuongnghiem.springbootrecipe.model.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class IngredientRepositoryIT {

    @Autowired
    IngredientRepository ingredientRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findByRecipe_IdAndIdHappy() {
        Ingredient ingredient = ingredientRepository.findByRecipe_IdAndId(2L, 16L).orElse(null);

        assertNotNull(ingredient);
        assertNotNull(ingredient.getDescription());
        assertNotNull(ingredient.getUom().getId());
    }
}