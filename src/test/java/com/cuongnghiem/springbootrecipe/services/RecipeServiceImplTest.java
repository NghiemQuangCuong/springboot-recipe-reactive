package com.cuongnghiem.springbootrecipe.services;


import com.cuongnghiem.springbootrecipe.command.RecipeCommand;
import com.cuongnghiem.springbootrecipe.converters.RecipeCommandToRecipe;
import com.cuongnghiem.springbootrecipe.converters.RecipeToRecipeCommand;
import com.cuongnghiem.springbootrecipe.model.Recipe;
import com.cuongnghiem.springbootrecipe.repositories.RecipeRepository;
import com.cuongnghiem.springbootrecipe.repositories.reactive.RecipeReactiveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Created by jt on 6/17/17.
 */
@ExtendWith(MockitoExtension.class)
public class RecipeServiceImplTest {

    @InjectMocks
    RecipeServiceImpl recipeService;
    @Mock
    RecipeReactiveRepository recipeRepository;
    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void getRecipes() throws Exception {

        Recipe recipe = new Recipe();
        when(recipeRepository.findAll()).thenReturn(Flux.just(recipe));

        Flux<Recipe> recipes = recipeService.getRecipes();

        assertEquals(recipes.count().block(), 1);
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    public void getRecipeById() {
        String recipeId = "1L";
        Recipe recipe = Recipe.builder().id(recipeId).build();

        when(recipeRepository.findById(recipeId)).thenReturn(Mono.just(recipe));

        Recipe recipeResult = recipeService.getRecipeById("1L").block();

        assertNotNull(recipeResult);
        assertEquals(recipe, recipeResult);
    }

    @Disabled
    void saveRecipeCommand() {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setDescription("Description");
        Recipe recipe = new Recipe();
        recipe.setDescription("Description");
        recipe.setId("1L");

        when(recipeRepository.save(any())).thenReturn(Mono.just(recipe));
        recipeCommand.setId("1L");
        when(recipeToRecipeCommand.convert(recipe)).thenReturn(recipeCommand);

        RecipeCommand recipeCommandResult = recipeService.saveRecipeCommand(recipeCommand).block();

        assertNotNull(recipeCommandResult.getId());
        assertEquals("Description", recipeCommandResult.getDescription());
    }

    @Test
    void getRecipeCommandById() {
        Recipe recipe = new Recipe();
        recipe.setId("1L");
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("1L");

        when(recipeRepository.findById(anyString())).thenReturn(Mono.just(recipe));
        when(recipeToRecipeCommand.convert(any())).thenReturn(recipeCommand);

        RecipeCommand command = recipeService.getRecipeCommandById("1L").block();

        assertNotNull(command);
        assertEquals("1L", command.getId());
    }

    @Test
    void deleteById() {
        // given
        Recipe recipe = new Recipe();
        recipe.setId("1L");
        when(recipeRepository.deleteById(anyString())).thenReturn(Mono.empty());
        // when
        recipeService.deleteById("1L");

        // then
        verify(recipeRepository, times(1)).deleteById(anyString());
    }
}