package com.cuongnghiem.springbootrecipe.repositories;

import com.cuongnghiem.springbootrecipe.model.Recipe;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by cuongnghiem on 27/09/2021
 **/

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
