package com.cuongnghiem.springbootrecipe.controllers;

import com.cuongnghiem.springbootrecipe.model.Recipe;
import com.cuongnghiem.springbootrecipe.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/recipe"})
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"/show/{id}"})
    public String getRecipe(@PathVariable String id, Model model) {

        try {
            Recipe recipe = recipeService.getRecipeById(Long.valueOf(id));
            if (recipe != null) {
                model.addAttribute("recipe", recipe);
                return "recipe/show";
            }
        } catch (NumberFormatException exception) {
            return "404";
        }

        return "404";
    }
}
