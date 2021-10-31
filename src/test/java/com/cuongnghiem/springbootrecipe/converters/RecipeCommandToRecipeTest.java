package com.cuongnghiem.springbootrecipe.converters;

import com.cuongnghiem.springbootrecipe.command.CategoryCommand;
import com.cuongnghiem.springbootrecipe.command.IngredientCommand;
import com.cuongnghiem.springbootrecipe.command.NotesCommand;
import com.cuongnghiem.springbootrecipe.command.RecipeCommand;
import com.cuongnghiem.springbootrecipe.model.Difficulty;
import com.cuongnghiem.springbootrecipe.model.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RecipeCommandToRecipeTest {

    public static final String ID = "1L";
    public static final String DESCRIPTION = "description";
    public static final Integer PREPTIME = 12;
    public static final Integer COOKTIME = 23;
    public static final Integer SERVINGS = 34;
    public static final String SOURCE = "source";
    public static final String URL = "url";
    public static final String DIRECTIONS = "directions";
    public static final byte[] IMAGE = new byte[2];
    public static final Difficulty DIFFICULTY = Difficulty.EASY;

    RecipeCommandToRecipe recipeCommandToRecipe;

    @BeforeEach
    void setUp() {
        recipeCommandToRecipe = new RecipeCommandToRecipe(
                new NotesCommandToNotes(),
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()),
                new CategoryCommandToCategory());
    }

    @Test
    void convert() {
        // given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(ID);
        recipeCommand.setDescription(DESCRIPTION);
        recipeCommand.setPrepTime(PREPTIME);
        recipeCommand.setCookTime(COOKTIME);
        recipeCommand.setServings(SERVINGS);
        recipeCommand.setSource(SOURCE);
        recipeCommand.setUrl(URL);
        recipeCommand.setDirections(DIRECTIONS);
        recipeCommand.setImage(IMAGE);
        recipeCommand.setDifficulty(DIFFICULTY);
        recipeCommand.setNotes(new NotesCommand());
        recipeCommand.getCategories().add(new CategoryCommand());
        recipeCommand.getIngredients().add(new IngredientCommand());

        // then
        Recipe recipe = recipeCommandToRecipe.convert(recipeCommand);

        assertEquals(ID, recipe.getId());
        assertEquals(DESCRIPTION, recipe.getDescription());
        assertEquals(PREPTIME, recipe.getPrepTime());
        assertEquals(COOKTIME, recipe.getCookTime());
        assertEquals(SERVINGS, recipe.getServings());
        assertEquals(SOURCE, recipe.getSource());
        assertEquals(URL, recipe.getUrl());
        assertEquals(DIRECTIONS, recipe.getDirections());
        assertEquals(IMAGE, recipe.getImage());
        assertEquals(DIFFICULTY, recipe.getDifficulty());
        assertNotNull(recipe.getNotes());
        assertEquals(1, recipe.getCategories().size());
        assertEquals(1, recipe.getIngredients().size());
    }
}