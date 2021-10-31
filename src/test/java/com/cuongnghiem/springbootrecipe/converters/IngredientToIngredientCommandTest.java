package com.cuongnghiem.springbootrecipe.converters;

import com.cuongnghiem.springbootrecipe.command.IngredientCommand;
import com.cuongnghiem.springbootrecipe.model.Ingredient;
import com.cuongnghiem.springbootrecipe.model.Recipe;
import com.cuongnghiem.springbootrecipe.model.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class IngredientToIngredientCommandTest {

    public static final String ID = "1L";
    public static final String DESCRIPTION = "Description";
    public static final BigDecimal AMOUNT = BigDecimal.TEN;
    public static final UnitOfMeasure UOM = new UnitOfMeasure();

    IngredientToIngredientCommand ingredientToIngredientCommand;
    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    @BeforeEach
    void setUp() {
        unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
        ingredientToIngredientCommand = new IngredientToIngredientCommand(unitOfMeasureToUnitOfMeasureCommand);
    }

    @Test
    void convert() {
        // given
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ID);
        ingredient.setDescription(DESCRIPTION);
        ingredient.setAmount(AMOUNT);
        ingredient.setUom(UOM);
        ingredient.setRecipe(new Recipe());

        // when
        IngredientCommand ingredientCommand = ingredientToIngredientCommand.convert(ingredient);

        // then
        assertEquals(ID, ingredientCommand.getId());
        assertEquals(DESCRIPTION, ingredientCommand.getDescription());
        assertEquals(AMOUNT, ingredientCommand.getAmount());
        assertNotNull(ingredientCommand.getUom());
    }
}