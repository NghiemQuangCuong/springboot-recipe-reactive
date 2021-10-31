package com.cuongnghiem.springbootrecipe.repositories;


import com.cuongnghiem.springbootrecipe.model.Recipe;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by jt on 6/13/17.
 */
public interface RecipeRepository extends CrudRepository<Recipe, String> {
}
