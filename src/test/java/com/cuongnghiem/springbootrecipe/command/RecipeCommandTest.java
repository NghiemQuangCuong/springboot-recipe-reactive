package com.cuongnghiem.springbootrecipe.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RecipeCommandTest {

    RecipeCommand recipeCommand;

    @BeforeEach
    void setUp() {
        recipeCommand = new RecipeCommand();
    }

    @Test
    void getIngredientStringSet() {
        IngredientCommand ingredient1 = new IngredientCommand();
        ingredient1.setAmount(BigDecimal.ONE);
        ingredient1.setUom(UnitOfMeasureCommand.builder().description("tablespoon").build());
        ingredient1.setDescription("Something");
        recipeCommand.getIngredients().add(ingredient1);

        recipeCommand.getIngredientStringSet().forEach(ingredient -> {
            assertEquals("1 tablespoon Something", ingredient);
        });
    }
}

