package com.cuongnghiem.springbootrecipe.repositories.reactive;

import com.cuongnghiem.springbootrecipe.model.Ingredient;
import com.cuongnghiem.springbootrecipe.model.Recipe;
import com.cuongnghiem.springbootrecipe.repositories.IngredientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class IngredientReactiveRepositoryIT {

    @Autowired
    IngredientReactiveRepository ingredientReactiveRepository;

    @BeforeEach
    void setUp() {
        ingredientReactiveRepository.save(Ingredient.builder().id("1X").recipe(Recipe.builder().id("2X").build()).build()).block();
    }

    @Test
    void findByRecipe_IdAndId() {
        Ingredient ingredient =
                ingredientReactiveRepository.findByRecipe_IdAndId("2X", "1X").block();

        assertNotNull(ingredient);
    }
}