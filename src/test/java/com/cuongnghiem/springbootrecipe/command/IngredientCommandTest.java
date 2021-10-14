package com.cuongnghiem.springbootrecipe.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IngredientCommandTest {

    IngredientCommand ingredientCommand;

    @BeforeEach
    void setUp() {
        ingredientCommand = new IngredientCommand();
    }

    @Test
    void toIngredientString() {
        ingredientCommand.setAmount(BigDecimal.TEN);
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setDescription("tablespoon");
        ingredientCommand.setUom(unitOfMeasureCommand);
        ingredientCommand.setDescription("of holy water");

        String result = ingredientCommand.toIngredientString();

        assertEquals("10 tablespoon of holy water", result);
    }
}