package com.cuongnghiem.springbootrecipe.controllers;

import com.cuongnghiem.springbootrecipe.command.RecipeCommand;
import com.cuongnghiem.springbootrecipe.converters.RecipeToRecipeCommand;
import com.cuongnghiem.springbootrecipe.model.Recipe;
import com.cuongnghiem.springbootrecipe.services.CategoryService;
import com.cuongnghiem.springbootrecipe.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import reactor.core.publisher.Mono;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {

    @InjectMocks
    RecipeController recipeController;
    @Mock
    RecipeService recipeService;
    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;
    @Mock
    CategoryService categoryService;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController)
                .setControllerAdvice(new ExceptionHandlerController())
                .build();
    }

    @Disabled
    @Test
    void getRecipe() throws Exception{
        RecipeCommand recipe = RecipeCommand.builder().id("1L").build();
        Recipe recipe1 = Recipe.builder().id("1L").build();
        when(recipeService.getRecipeById(anyString())).thenReturn(Mono.just(recipe1));

        mockMvc.perform(get("/recipe/1L/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"));
    }

    @Test
    void newRecipe() throws Exception{

        when(categoryService.getAllCategoryCommand()).thenReturn(new HashSet<>());

        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/new_or_update"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void testPostNewRecipe() throws Exception{
        RecipeCommand recipeCommand = RecipeCommand.builder().id("1L").build();

        when(recipeService.saveRecipeCommand(any())).thenReturn(Mono.just(recipeCommand));

        mockMvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some String")
                .param("directions", "Some Directions"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/1L/show"));
    }

    @Test
    void testPostNewRecipeFail() throws Exception{

        when(recipeService.getRecipeCommandById(any())).thenReturn(Mono.just(new RecipeCommand()));

        mockMvc.perform(post("/recipe")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/new_or_update"));
    }

    @Test
    void testGetUpdateRecipe() throws Exception {
        RecipeCommand recipeCommand = RecipeCommand.builder().id("1L").build();

        when(recipeService.getRecipeCommandById(anyString())).thenReturn(Mono.just(recipeCommand));
        when(categoryService.getAllCategoryCommand()).thenReturn(new HashSet<>());

        mockMvc.perform(get("/recipe/1L/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/new_or_update"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void testDeleteRecipeWithValidId() throws Exception {
        mockMvc.perform(post("/recipe/1L/delete")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }
}