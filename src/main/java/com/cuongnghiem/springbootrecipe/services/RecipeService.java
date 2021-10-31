package com.cuongnghiem.springbootrecipe.services;

import com.cuongnghiem.springbootrecipe.command.RecipeCommand;
import com.cuongnghiem.springbootrecipe.model.Recipe;

import java.util.Set;

/**
 * Created by jt on 6/13/17.
 */
public interface RecipeService {

    Recipe save(Recipe recipe);
    Set<Recipe> getRecipes();
    Recipe getRecipeById(String id);
    RecipeCommand getRecipeCommandById(String id);
    RecipeCommand saveRecipeCommand(RecipeCommand command);
    void deleteById(String id);
}
