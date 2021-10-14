package com.cuongnghiem.springbootrecipe.repositories;

import com.cuongnghiem.springbootrecipe.model.Ingredient;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Created by cuongnghiem on 28/09/2021
 **/

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
    Optional<Ingredient> findByRecipe_IdAndId(Long recipeId, Long id);
}
