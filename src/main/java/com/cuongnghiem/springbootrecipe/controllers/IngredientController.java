package com.cuongnghiem.springbootrecipe.controllers;

import com.cuongnghiem.springbootrecipe.command.IngredientCommand;
import com.cuongnghiem.springbootrecipe.command.RecipeCommand;
import com.cuongnghiem.springbootrecipe.services.IngredientService;
import com.cuongnghiem.springbootrecipe.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/recipe")
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @GetMapping("/{id}/ingredient/show")
    public String showIngredients(@PathVariable String id, Model model) {
        try {
            RecipeCommand recipeCommand = recipeService.getRecipeCommandById(Long.valueOf(id));
            if (recipeCommand != null) {
                model.addAttribute("recipe", recipeCommand);
                return "recipe/ingredient/show";
            }
            return "404";
        } catch (NumberFormatException exception) {
            return "404";
        }
    }

    @GetMapping("/{recipeId}/ingredient/{ingredientId}/show")
    public String viewIngredient(@PathVariable String recipeId,
                                 @PathVariable String ingredientId,
                                 Model model) {
        try {
            IngredientCommand ingredientCommand =
                    ingredientService.findCommandByIdWithRecipeId(Long.valueOf(ingredientId), Long.valueOf(recipeId));
            if (ingredientCommand != null) {
                model.addAttribute("ingredient", ingredientCommand);
                return "recipe/ingredient/view";
            }
            return "404";
        } catch (NumberFormatException exception) {
            return "404";
        }
    }
}
