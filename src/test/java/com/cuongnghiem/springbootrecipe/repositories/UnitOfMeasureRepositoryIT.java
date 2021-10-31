package com.cuongnghiem.springbootrecipe.repositories;

import com.cuongnghiem.springbootrecipe.bootstrap.RecipeBootstrap;
import com.cuongnghiem.springbootrecipe.model.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataMongoTest
class UnitOfMeasureRepositoryIT {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    NotesRepository notesRepository;
    @Autowired
    IngredientRepository ingredientRepository;

    RecipeBootstrap recipeBootstrap;


    @BeforeEach
    void setUp() {
        if (categoryRepository.count()==0L) {
            recipeBootstrap = new RecipeBootstrap(categoryRepository, recipeRepository, unitOfMeasureRepository,notesRepository,ingredientRepository);
            recipeBootstrap.onApplicationEvent(null);
        }
    }

    @Test
    void findByDescription() {
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Pinch");

        assertEquals("Pinch", unitOfMeasureOptional.get().getDescription());
    }
}