package com.cuongnghiem.springbootrecipe.bootstrap;

import com.cuongnghiem.springbootrecipe.model.*;
import com.cuongnghiem.springbootrecipe.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Created by cuongnghiem on 28/09/2021
 **/
@Component
public class RecipeBootstrap implements CommandLineRunner {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final NotesRepository notesRepository;
    private final IngredientRepository ingredientRepository;

    public RecipeBootstrap(RecipeRepository recipeRepository, CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository, NotesRepository notesRepository, IngredientRepository ingredientRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.notesRepository = notesRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        guacamoleRecipeLoad();

    }

    private void guacamoleRecipeLoad() {
        Category mexican = categoryRepository.findByDescription("Mexican").get();
        UnitOfMeasure teaspoon = unitOfMeasureRepository.findByDescription("Teaspoon").get();
        UnitOfMeasure tablespoon = unitOfMeasureRepository.findByDescription("Tablespoon").get();
        UnitOfMeasure pinch = unitOfMeasureRepository.findByDescription("Pinch").get();

        Notes guacamoleNotes = new Notes();
        guacamoleNotes.setRecipeNotes("Be careful handling chilis! If using, it's best to wear food-safe gloves. If no gloves are available, wash your hands thoroughly after handling, and do not touch your eyes or the area near your eyes for several hours afterwards.");
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setAmount(BigDecimal.valueOf(2));
        ingredient1.setDescription("ripe avocados");
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setAmount(BigDecimal.valueOf(0.25));
        ingredient2.setUom(teaspoon);
        ingredient2.setDescription("salt, plus more to taste");
        Ingredient ingredient3 = new Ingredient();
        ingredient3.setAmount(BigDecimal.valueOf(1));
        ingredient3.setUom(tablespoon);
        ingredient3.setDescription("fresh lime or lemon juice");

        Recipe guacamole = new Recipe();
        guacamole.setDescription("A Mexican food");
        guacamole.getCategories().add(mexican);
        guacamole.setCookTime("0 mins");
        guacamole.setPrepTime("10 mins");
        guacamole.setDifficulty(Difficulty.EASY);
        guacamole.setServings(4);
        guacamole.setNotes(guacamoleNotes);
        guacamole.getIngredients().add(ingredient1);
        ingredient1.setRecipe(guacamole);
        guacamole.getIngredients().add(ingredient2);
        ingredient2.setRecipe(guacamole);

        recipeRepository.save(guacamole);

    }
}
