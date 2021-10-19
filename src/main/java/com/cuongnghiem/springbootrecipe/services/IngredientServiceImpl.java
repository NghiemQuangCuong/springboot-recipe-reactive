package com.cuongnghiem.springbootrecipe.services;

import com.cuongnghiem.springbootrecipe.command.IngredientCommand;
import com.cuongnghiem.springbootrecipe.converters.IngredientCommandToIngredient;
import com.cuongnghiem.springbootrecipe.converters.IngredientToIngredientCommand;
import com.cuongnghiem.springbootrecipe.exception.NotFoundException;
import com.cuongnghiem.springbootrecipe.model.Ingredient;
import com.cuongnghiem.springbootrecipe.repositories.IngredientRepository;
import com.cuongnghiem.springbootrecipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class IngredientServiceImpl implements IngredientService{

    private final IngredientRepository ingredientRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final RecipeService recipeService;
    private final UnitOfMeasureService unitOfMeasureService;
    private final RecipeRepository recipeRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository, IngredientToIngredientCommand ingredientToIngredientCommand, IngredientCommandToIngredient ingredientCommandToIngredient, RecipeService recipeService, UnitOfMeasureService unitOfMeasureService, RecipeRepository recipeRepository) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.recipeService = recipeService;
        this.unitOfMeasureService = unitOfMeasureService;
        this.recipeRepository = recipeRepository;
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

    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        try {
            Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
            ingredient.setRecipe(recipeService.getRecipeById(ingredientCommand.getRecipeId()));
            ingredient.setUom(unitOfMeasureService.getUOMById(ingredientCommand.getUom().getId()));
            ingredientRepository.save(ingredient);

            return ingredientToIngredientCommand.convert(ingredient);
        } catch (RuntimeException exception) {
            return null;
        }
    }

    @Override
    public void deleteByIdAndRecipeId(Long id, Long recipeId) {
        Ingredient ingredient =
                ingredientRepository.findByRecipe_IdAndId(recipeId, id).orElse(null);
        if (ingredient == null)
            throw new NotFoundException("Cannot find ingredient with recipe. ingredientId = " + id
            + ", recipeId = " + recipeId);

        ingredientRepository.delete(ingredient);
    }
}
