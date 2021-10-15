package com.cuongnghiem.springbootrecipe.services;

import com.cuongnghiem.springbootrecipe.command.IngredientCommand;
import com.cuongnghiem.springbootrecipe.converters.IngredientCommandToIngredient;
import com.cuongnghiem.springbootrecipe.converters.IngredientToIngredientCommand;
import com.cuongnghiem.springbootrecipe.model.Ingredient;
import com.cuongnghiem.springbootrecipe.repositories.IngredientRepository;
import com.cuongnghiem.springbootrecipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
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
    @Transactional
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
//        Recipe recipe = recipeService.getRecipeById(ingredientCommand.getRecipeId());
//
//        if (recipe == null) {
//            log.debug("cannot find recipe");
//            return null;
//        }
//
//        Ingredient foundIngredient =
//                recipe.getIngredients().stream()
//                        .filter(ingredient -> Objects.equals(ingredient.getId(), ingredientCommand.getId()))
//                        .findFirst().orElse(null);
//
//        if (ingredientCommand.getId() != null && foundIngredient == null)
//        {
//            log.debug("cannot find ingredient in recipe");
//            return null;
//        }
//
//        UnitOfMeasure uom = unitOfMeasureService.getUOMById(ingredientCommand.getUom().getId());
//        if (uom == null) {
//            log.debug("cannot find UoM");
//            return null;
//        }
//
//        if (foundIngredient == null){
//            Ingredient newIngredient = ingredientCommandToIngredient.convert(ingredientCommand);
//            newIngredient.setUom(uom);
//            recipe.getIngredients().add(newIngredient);
//        }
//        else {
//            foundIngredient.setDescription(ingredientCommand.getDescription());
//            foundIngredient.setAmount(ingredientCommand.getAmount());
//            foundIngredient.setUom(uom);
//        }
//
//        recipeRepository.save(recipe);
//
//        Ingredient savedIngredient = recipe.getIngredients().stream()
//                .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
//                .findFirst().orElse(null);

//        return ingredientToIngredientCommand.convert(savedIngredient) ;
    }
}
