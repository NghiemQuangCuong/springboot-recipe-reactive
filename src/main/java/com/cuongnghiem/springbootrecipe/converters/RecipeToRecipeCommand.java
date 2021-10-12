package com.cuongnghiem.springbootrecipe.converters;

import com.cuongnghiem.springbootrecipe.command.RecipeCommand;
import com.cuongnghiem.springbootrecipe.model.Recipe;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private final NotesToNotesCommand notesToNotesCommand;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final CategoryToCategoryCommand categoryToCategoryCommand;

    public RecipeToRecipeCommand(NotesToNotesCommand notesToNotesCommand, IngredientToIngredientCommand ingredientToIngredientCommand, CategoryToCategoryCommand categoryToCategoryCommand) {
        this.notesToNotesCommand = notesToNotesCommand;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.categoryToCategoryCommand = categoryToCategoryCommand;
    }

    @Nullable
    @Override
    public RecipeCommand convert(Recipe recipe) {
        if (recipe == null)
            return null;

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setUrl(recipe.getUrl());
        recipeCommand.setServings(recipe.getServings());
        recipeCommand.setId(recipe.getId());
        recipeCommand.setCookTime(recipe.getCookTime());
        recipeCommand.setDescription(recipe.getDescription());
        recipeCommand.setDifficulty(recipe.getDifficulty());
        recipeCommand.setDirections(recipe.getDirections());
        recipeCommand.setPrepTime(recipe.getPrepTime());
        recipeCommand.setImage(recipe.getImage());
        recipeCommand.setSource(recipe.getSource());
        recipeCommand.setNotes(notesToNotesCommand.convert(recipe.getNotes()));

        recipe.getIngredients().forEach(ingredient -> {
            recipeCommand.getIngredients().add(ingredientToIngredientCommand.convert(ingredient));
        });
        recipe.getCategories().forEach(category -> {
            recipeCommand.getCategories().add(categoryToCategoryCommand.convert(category));
        });

        return recipeCommand;
    }
}
