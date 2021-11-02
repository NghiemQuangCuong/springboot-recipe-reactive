package com.cuongnghiem.springbootrecipe.services;

import com.cuongnghiem.springbootrecipe.command.RecipeCommand;
import com.cuongnghiem.springbootrecipe.model.Recipe;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

/**
 * Created by jt on 6/13/17.
 */
public interface RecipeService {

    Mono<Recipe> save(Recipe recipe);
    Flux<Recipe> getRecipes();
    Mono<Recipe> getRecipeById(String id);
    Mono<RecipeCommand> getRecipeCommandById(String id);
    Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command);
    void deleteById(String id);
}
