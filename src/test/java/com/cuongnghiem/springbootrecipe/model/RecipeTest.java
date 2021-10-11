package com.cuongnghiem.springbootrecipe.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RecipeTest {

    Recipe recipe;

    @BeforeEach
    void setUp() {
        recipe = new Recipe();
    }

    @Test
    void getIngredientStringSet() {
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setAmount(BigDecimal.ONE);
        ingredient1.setUom(UnitOfMeasure.builder().description("tablespoon").build());
        ingredient1.setDescription("Something");
        recipe.getIngredients().add(ingredient1);

        recipe.getIngredientStringSet().forEach(ingredient -> {
            assertEquals("1 tablespoon Something", ingredient);
        });
    }
}