package com.cuongnghiem.springbootrecipe.controllers;

import com.cuongnghiem.springbootrecipe.command.RecipeCommand;
import com.cuongnghiem.springbootrecipe.converters.RecipeToRecipeCommand;
import com.cuongnghiem.springbootrecipe.model.Recipe;
import com.cuongnghiem.springbootrecipe.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class IndexControllerTest {

    @InjectMocks
    private IndexController indexController;

    @Mock
    private RecipeService recipeService;
    @Mock
    private RecipeToRecipeCommand recipeToRecipeCommand;
    @Mock
    private Model model;

    private String newRecipeCommandId = "1L";

    @BeforeEach
    void setUp() {
    }

    @Test
    void testMockMVC() throws Exception{
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void getIndexPage() {
        //given
        Set<Recipe> recipeSet = new HashSet<>();
        Recipe recipe = new Recipe();
        recipe.setId("1L");
        recipeSet.add(recipe);
        Recipe recipe1 = new Recipe();
        recipe1.setId("2L");
        recipeSet.add(recipe1);

        when(recipeService.getRecipes()).thenReturn(recipeSet);
        when(recipeToRecipeCommand.convert(recipe)).thenReturn(newRecipeCommand());
        when(recipeToRecipeCommand.convert(recipe1)).thenReturn(newRecipeCommand());


        ArgumentCaptor<Set<RecipeCommand>> setArgumentCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String result = indexController.getIndexPage(model);

        //then
        assertEquals("index", result);
        verify(recipeService, times(1)).getRecipes();
        verify(model, times(1)).addAttribute(eq("recipes"), setArgumentCaptor.capture());
        assertEquals(1, setArgumentCaptor.getValue().size());
    }

    private RecipeCommand newRecipeCommand() {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(newRecipeCommandId + "a");
        return recipeCommand;
    }
}