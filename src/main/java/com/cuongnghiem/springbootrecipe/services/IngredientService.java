package com.cuongnghiem.springbootrecipe.services;

import com.cuongnghiem.springbootrecipe.command.IngredientCommand;

public interface IngredientService {
    IngredientCommand findIngredientCommandById(Long id);
    IngredientCommand findCommandByIdWithRecipeId(Long id, Long recipeId);
    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
    void deleteByIdAndRecipeId(Long id, Long recipeId);
}
