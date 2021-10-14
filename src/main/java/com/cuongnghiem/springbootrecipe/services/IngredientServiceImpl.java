package com.cuongnghiem.springbootrecipe.services;

import com.cuongnghiem.springbootrecipe.command.IngredientCommand;
import com.cuongnghiem.springbootrecipe.converters.IngredientToIngredientCommand;
import com.cuongnghiem.springbootrecipe.model.Ingredient;
import com.cuongnghiem.springbootrecipe.repositories.IngredientRepository;
import org.springframework.stereotype.Service;

@Service
public class IngredientServiceImpl implements IngredientService{

    private final IngredientRepository ingredientRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    public IngredientServiceImpl(IngredientRepository ingredientRepository, IngredientToIngredientCommand ingredientToIngredientCommand) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }

    @Override
    public IngredientCommand findIngredientCommandById(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id).orElse(null);
        if (ingredient == null)
            return null;
        return ingredientToIngredientCommand.convert(ingredient);
    }

    @Override
    public IngredientCommand findCommandByIdWithRecipeId(Long id, Long recipeId) {
        Ingredient ingredient = ingredientRepository.findByRecipe_IdAndId(recipeId, id).orElse(null);
        if (ingredient == null)
            return null;
        return ingredientToIngredientCommand.convert(ingredient);
    }
}
