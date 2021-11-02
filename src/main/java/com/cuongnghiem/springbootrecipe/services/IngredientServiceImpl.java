package com.cuongnghiem.springbootrecipe.services;

import com.cuongnghiem.springbootrecipe.command.IngredientCommand;
import com.cuongnghiem.springbootrecipe.converters.IngredientCommandToIngredient;
import com.cuongnghiem.springbootrecipe.converters.IngredientToIngredientCommand;
import com.cuongnghiem.springbootrecipe.exception.NotFoundException;
import com.cuongnghiem.springbootrecipe.model.Ingredient;
import com.cuongnghiem.springbootrecipe.model.Recipe;
import com.cuongnghiem.springbootrecipe.repositories.reactive.IngredientReactiveRepository;
import com.cuongnghiem.springbootrecipe.repositories.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@Transactional
public class IngredientServiceImpl implements IngredientService{

    private final IngredientReactiveRepository ingredientRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final RecipeService recipeService;
    private final UnitOfMeasureService unitOfMeasureService;
    private final RecipeReactiveRepository recipeRepository;

    public IngredientServiceImpl(IngredientReactiveRepository ingredientRepository, IngredientToIngredientCommand ingredientToIngredientCommand, IngredientCommandToIngredient ingredientCommandToIngredient, RecipeService recipeService, UnitOfMeasureService unitOfMeasureService, RecipeReactiveRepository recipeRepository) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.recipeService = recipeService;
        this.unitOfMeasureService = unitOfMeasureService;
        this.recipeRepository = recipeRepository;
    }


    @Override
    public Mono<IngredientCommand> findIngredientCommandById(String id) {

        return ingredientRepository.findById(id)
                .map(ingredientToIngredientCommand::convert);
    }

    @Override
    public Mono<IngredientCommand> findCommandByIdWithRecipeId(String id, String recipeId) {
        return ingredientRepository.findByRecipe_IdAndId(recipeId, id)
                .map(ingredientToIngredientCommand::convert);
    }

    @Override
    public Mono<IngredientCommand> saveIngredientCommand(IngredientCommand ingredientCommand) {

        Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
        ingredient.setRecipe(recipeService.getRecipeById(ingredientCommand.getRecipeId()).block());
        ingredient.setUom(unitOfMeasureService.getUOMById(ingredientCommand.getUom().getId()).block());
        Mono<IngredientCommand> result = ingredientRepository
                .save(ingredient).map(ingredientToIngredientCommand::convert);

        Recipe recipe = recipeService.getRecipeById(ingredientCommand.getRecipeId()).block();
        recipe.getIngredients().removeIf(ingredient1 -> ingredient1.getId().equals(ingredient.getId()));
        recipe.getIngredients().add(ingredient);
        recipeRepository.save(recipe).block();
        return result;
    }

    @Override
    public void deleteByIdAndRecipeId(String id, String recipeId) {
        Ingredient ingredient =
                ingredientRepository.findByRecipe_IdAndId(recipeId, id).block();
        if (ingredient == null)
            throw new NotFoundException("Cannot find ingredient with recipe. ingredientId = " + id
            + ", recipeId = " + recipeId);

        Recipe recipe = recipeService.getRecipeById(recipeId).block();
        recipe.getIngredients().removeIf(ingredient1 -> ingredient1.getId().equals(ingredient.getId()));
        recipeRepository.save(recipe).block();

        ingredientRepository.delete(ingredient).block();
    }
}
