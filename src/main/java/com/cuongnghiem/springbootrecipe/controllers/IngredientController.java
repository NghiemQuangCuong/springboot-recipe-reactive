package com.cuongnghiem.springbootrecipe.controllers;

import com.cuongnghiem.springbootrecipe.command.RecipeCommand;
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

    public IngredientController(RecipeService recipeService) {
        this.recipeService = recipeService;
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
}
