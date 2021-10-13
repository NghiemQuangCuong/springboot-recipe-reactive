package com.cuongnghiem.springbootrecipe.controllers;

import com.cuongnghiem.springbootrecipe.command.RecipeCommand;
import com.cuongnghiem.springbootrecipe.converters.RecipeToRecipeCommand;
import com.cuongnghiem.springbootrecipe.model.Recipe;
import com.cuongnghiem.springbootrecipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping({"/recipe"})
public class RecipeController {

    private final RecipeService recipeService;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeController(RecipeService recipeService, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeService = recipeService;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @GetMapping({"/{id}/show"})
    public String getRecipe(@PathVariable String id, Model model) {
        log.debug("[GET] - /recipe/" + id + "/show");
        try {
            Recipe recipe = recipeService.getRecipeById(Long.valueOf(id));
            if (recipe != null) {
                model.addAttribute("recipe", recipeToRecipeCommand.convert(recipe));
                return "recipe/show";
            }
            return "404";
        } catch (NumberFormatException exception) {
            return "404";
        }
    }

    @GetMapping({"/new"})
    public String newRecipe(Model model) {
        log.debug("[GET] - /recipe/new");
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/new_or_update";
    }

    @GetMapping("/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model) {
        log.debug("[GET] - /recipe/" + id + "/update");
        try {
            RecipeCommand recipeCommand = recipeService.getRecipeCommandById(Long.valueOf(id));
            if (recipeCommand != null) {
                model.addAttribute("recipe", recipeCommand);
                return "recipe/new_or_update";
            }
           return "404";
        }
        catch (NumberFormatException ex) {
            return "404";
        }
    }

    @PostMapping({"", "/"})
    public String saveOrUpdate(@ModelAttribute RecipeCommand command){
        log.debug("[POST] - /recipe");
        RecipeCommand recipeCommand = recipeService.saveRecipeCommand(command);

        return "redirect:/recipe/" + recipeCommand.getId() + "/show";
    }

    @PostMapping("/{id}/delete")
    public String deleteRecipe(@PathVariable String id) {
        log.debug("[POST] - Delete recipe id = " + id);
        try {
            recipeService.deleteById(Long.valueOf(id));
            return "redirect:/";
        } catch (NumberFormatException exception) {
            log.debug("Failed to delete recipe id = " + id);
            return "404";
        }
    }
}
