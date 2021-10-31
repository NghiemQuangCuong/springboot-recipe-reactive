package com.cuongnghiem.springbootrecipe.services;

import com.cuongnghiem.springbootrecipe.command.IngredientCommand;

public interface IngredientService {
    IngredientCommand findIngredientCommandById(String id);
    IngredientCommand findCommandByIdWithRecipeId(String id, String recipeId);
    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
    void deleteByIdAndRecipeId(String id, String recipeId);
}
