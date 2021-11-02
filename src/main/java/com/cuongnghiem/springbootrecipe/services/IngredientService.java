package com.cuongnghiem.springbootrecipe.services;

import com.cuongnghiem.springbootrecipe.command.IngredientCommand;
import reactor.core.publisher.Mono;

public interface IngredientService {
    Mono<IngredientCommand> findIngredientCommandById(String id);
    Mono<IngredientCommand> findCommandByIdWithRecipeId(String id, String recipeId);
    Mono<IngredientCommand> saveIngredientCommand(IngredientCommand ingredientCommand);
    void deleteByIdAndRecipeId(String id, String recipeId);
}
