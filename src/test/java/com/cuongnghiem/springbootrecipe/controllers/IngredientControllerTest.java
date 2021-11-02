package com.cuongnghiem.springbootrecipe.controllers;

import com.cuongnghiem.springbootrecipe.command.IngredientCommand;
import com.cuongnghiem.springbootrecipe.command.RecipeCommand;
import com.cuongnghiem.springbootrecipe.command.UnitOfMeasureCommand;
import com.cuongnghiem.springbootrecipe.model.Recipe;
import com.cuongnghiem.springbootrecipe.services.IngredientService;
import com.cuongnghiem.springbootrecipe.services.RecipeService;
import com.cuongnghiem.springbootrecipe.services.UnitOfMeasureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import reactor.core.publisher.Flux;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class IngredientControllerTest {

    @InjectMocks
    IngredientController ingredientController;
    @Mock
    RecipeService recipeService;
    @Mock
    IngredientService ingredientService;
    @Mock
    UnitOfMeasureService uomService;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
    }

    @Test
    void viewIngredienstOfARecipe() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();

        when(recipeService.getRecipeCommandById(anyString())).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipe/1L/ingredient/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void viewAnIngredient() throws Exception {
        IngredientCommand ingredientCommand = new IngredientCommand();

        when(ingredientService.findCommandByIdWithRecipeId(anyString(), anyString())).thenReturn(ingredientCommand);

        mockMvc.perform(get("/recipe/1L/ingredient/1L/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/view"))
                .andExpect(model().attributeExists("ingredient"));
    }

    @Test
    void getUpdateViewOfAnIngredient() throws Exception {
        IngredientCommand ingredientCommand = new IngredientCommand();

        when(ingredientService.findCommandByIdWithRecipeId(anyString(), anyString())).thenReturn(ingredientCommand);
        when(uomService.getAllUoMCommand()).thenReturn(Flux.just(new UnitOfMeasureCommand()));

        mockMvc.perform(get("/recipe/1L/ingredient/1L/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/new_or_update"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("listUOM"));
    }

    @Test
    void saveOrUpdate() throws Exception {
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId("2L");
        ingredientCommand.setRecipeId("3L");

        when(ingredientService.saveIngredientCommand(any())).thenReturn(ingredientCommand);

        mockMvc.perform(post("/recipe/ingredient/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/3L/ingredient/2L/show"));
    }

    @Test
    void newIngredient() throws Exception {

        when(recipeService.getRecipeById(anyString())).thenReturn(new Recipe());
        when(uomService.getAllUoMCommand()).thenReturn(Flux.just(new UnitOfMeasureCommand()));

        mockMvc.perform(get("/recipe/1L/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/new_or_update"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("listUOM"));
    }

    @Test
    void deleteIngredient() throws Exception {

        mockMvc.perform(post("/recipe/1L/ingredient/1L/delete")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/1L/ingredient/show"));
    }
}