package com.cuongnghiem.springbootrecipe.services;

import com.cuongnghiem.springbootrecipe.model.Recipe;

import java.util.Set;

/**
 * Created by jt on 6/13/17.
 */
public interface RecipeService {

    Set<Recipe> getRecipes();
    Recipe getRecipeById(Long id);
}
