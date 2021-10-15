package com.cuongnghiem.springbootrecipe.controllers;

import com.cuongnghiem.springbootrecipe.command.CategoryCommand;
import com.cuongnghiem.springbootrecipe.command.RecipeCommand;
import com.cuongnghiem.springbootrecipe.converters.RecipeToRecipeCommand;
import com.cuongnghiem.springbootrecipe.model.Recipe;
import com.cuongnghiem.springbootrecipe.services.CategoryService;
import com.cuongnghiem.springbootrecipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Slf4j
@Controller
@RequestMapping({"/recipe"})
public class RecipeController {

    private final RecipeService recipeService;
    private final RecipeToRecipeCommand recipeToRecipeCommand;
    private final CategoryService categoryService;

    public RecipeController(RecipeService recipeService, RecipeToRecipeCommand recipeToRecipeCommand, CategoryService categoryService) {
        this.recipeService = recipeService;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
        this.categoryService = categoryService;
    }

    @GetMapping({"/{id}/show"})
    public String getRecipe(@PathVariable String id, Model model) {
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
        Set<CategoryCommand> categoryCommands =
            categoryService.getAllCategoryCommand();

        model.addAttribute("recipe", new RecipeCommand());
        model.addAttribute("listCategory", categoryCommands);

        return "recipe/new_or_update";
    }

    @GetMapping("/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model) {
        try {
            RecipeCommand recipeCommand = recipeService.getRecipeCommandById(Long.valueOf(id));
            if (recipeCommand != null) {
                Set<CategoryCommand> categoryCommands =
                        categoryService.getAllCategoryCommand();

                model.addAttribute("recipe", recipeCommand);
                model.addAttribute("listCategory", categoryCommands);

                return "recipe/new_or_update";
            }
           return "404";
        }
        catch (NumberFormatException ex) {
            return "404";
        }
    }

    @PostMapping({"", "/"})
    public String saveOrUpdate(@ModelAttribute RecipeCommand command,
                               @RequestParam(value = "cats", required = false) Set<Long> cats){
        if (cats != null)
            cats.forEach(catId -> {
                command.getCategories().add(categoryService.findById(catId));
            });
        RecipeCommand recipeCommand = recipeService.saveRecipeCommand(command);

        return "redirect:/recipe/" + recipeCommand.getId() + "/show";
    }

    @PostMapping("/{id}/delete")
    public String deleteRecipe(@PathVariable String id) {
        try {
            recipeService.deleteById(Long.valueOf(id));
            return "redirect:/";
        } catch (NumberFormatException exception) {
            log.debug("Failed to delete recipe id = " + id);
            return "404";
        }
    }
}
