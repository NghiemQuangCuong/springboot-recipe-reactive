package com.cuongnghiem.springbootrecipe.repositories.reactive;

import com.cuongnghiem.springbootrecipe.model.Ingredient;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * Created by cuongnghiem on 02/11/2021
 **/

public interface IngredientReactiveRepository extends ReactiveMongoRepository<Ingredient, String> {
    Mono<Ingredient> findByRecipe_IdAndId(String recipeId, String id);
}
