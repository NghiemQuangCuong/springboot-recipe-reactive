package com.cuongnghiem.springbootrecipe.converters;

import com.cuongnghiem.springbootrecipe.command.RecipeCommand;
import com.cuongnghiem.springbootrecipe.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RecipeToRecipeCommandTest {
    public static final Long ID = 1L;
    public static final String DESCRIPTION = "description";
    public static final Integer PREPTIME = 12;
    public static final Integer COOKTIME = 23;
    public static final Integer SERVINGS = 34;
    public static final String SOURCE = "source";
    public static final String URL = "url";
    public static final String DIRECTIONS = "directions";
    public static final Byte[] IMAGE = new Byte[2];
    public static final Difficulty DIFFICULTY = Difficulty.EASY;

    RecipeToRecipeCommand recipeToRecipeCommand;

    @BeforeEach
    void setUp() {
        recipeToRecipeCommand = new RecipeToRecipeCommand(
                new NotesToNotesCommand(),
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
                new CategoryToCategoryCommand());
    }

    @Test
    void convert() {
        // given
        Recipe recipe = new Recipe();
        recipe.setId(ID);
        recipe.setDescription(DESCRIPTION);
        recipe.setPrepTime(PREPTIME);
        recipe.setCookTime(COOKTIME);
        recipe.setServings(SERVINGS);
        recipe.setSource(SOURCE);
        recipe.setUrl(URL);
        recipe.setDirections(DIRECTIONS);
        recipe.setImage(IMAGE);
        recipe.setDifficulty(DIFFICULTY);
        recipe.setNotes(new Notes());
        recipe.getCategories().add(new Category());
        Ingredient ingredient = new Ingredient();
        ingredient.setRecipe(recipe);
        recipe.getIngredients().add(ingredient);


        // then
        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);

        assertEquals(ID, recipeCommand.getId());
        assertEquals(DESCRIPTION, recipeCommand.getDescription());
        assertEquals(PREPTIME, recipeCommand.getPrepTime());
        assertEquals(COOKTIME, recipeCommand.getCookTime());
        assertEquals(SERVINGS, recipeCommand.getServings());
        assertEquals(SOURCE, recipeCommand.getSource());
        assertEquals(URL, recipeCommand.getUrl());
        assertEquals(DIRECTIONS, recipeCommand.getDirections());
        assertEquals(IMAGE, recipeCommand.getImage());
        assertEquals(DIFFICULTY, recipeCommand.getDifficulty());
        assertNotNull(recipeCommand.getNotes());
        assertEquals(1, recipeCommand.getCategories().size());
        assertEquals(1, recipeCommand.getIngredients().size());
    }
}