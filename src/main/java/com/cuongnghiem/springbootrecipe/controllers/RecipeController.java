package com.cuongnghiem.springbootrecipe.controllers;

import com.cuongnghiem.springbootrecipe.command.CategoryCommand;
import com.cuongnghiem.springbootrecipe.command.RecipeCommand;
import com.cuongnghiem.springbootrecipe.converters.RecipeToRecipeCommand;
import com.cuongnghiem.springbootrecipe.exception.NotFoundException;
import com.cuongnghiem.springbootrecipe.model.Recipe;
import com.cuongnghiem.springbootrecipe.services.CategoryService;
import com.cuongnghiem.springbootrecipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.Set;

@Slf4j
@Controller
@RequestMapping({"/recipe"})
public class RecipeController {

    public static final String RECIPE_NEW_OR_UPDATE = "recipe/new_or_update";
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
        Mono<RecipeCommand> recipeCommand = recipeService
                .getRecipeById(id)
                .map(recipeToRecipeCommand::convert);

        model.addAttribute("recipe", recipeCommand.block());
        return "recipe/show";
    }

    @GetMapping({"/new"})
    public String newRecipe(Model model) {
        Flux<CategoryCommand> categoryCommands =
                categoryService.getAllCategoryCommand();

        model.addAttribute("recipe", new RecipeCommand());
        model.addAttribute("listCategory", categoryCommands);

        return RECIPE_NEW_OR_UPDATE;
    }

    @GetMapping("/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model) {
        Mono<RecipeCommand> recipeCommand = recipeService.getRecipeCommandById(id);
        if (recipeCommand != null) {
            Flux<CategoryCommand> categoryCommands =
                    categoryService.getAllCategoryCommand();

            model.addAttribute("recipe", recipeCommand.block());
            model.addAttribute("listCategory", categoryCommands.collectList().block());

            return RECIPE_NEW_OR_UPDATE;
        }
       throw new NotFoundException("Recipe not found, id = " + id);
    }

    @PostMapping("")
    public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand command,
                               BindingResult bindingResult,
                               @RequestParam(value = "cats", required = false) Set<String> cats,
                               Model model
                               ){
        if (cats != null)
            cats.forEach(catId -> {
                command.getCategories().add(categoryService.findById(catId).block());
            });

        if (bindingResult.hasErrors()) {
            Flux<CategoryCommand> categoryCommands =
                    categoryService.getAllCategoryCommand();
            model.addAttribute("listCategory", categoryCommands.collectList().block());
            RecipeCommand fullRecipe = recipeService.getRecipeCommandById(command.getId()).block();
            command.setIngredients(fullRecipe.getIngredients());
            return RECIPE_NEW_OR_UPDATE;
        }
        RecipeCommand recipeCommand = recipeService.saveRecipeCommand(command).block();
        return "redirect:/recipe/" + recipeCommand.getId() + "/show";
    }

    @PostMapping("/{id}/delete")
    public String deleteRecipe(@PathVariable String id) {
        recipeService.deleteById(id);
        return "redirect:/";
    }
}
