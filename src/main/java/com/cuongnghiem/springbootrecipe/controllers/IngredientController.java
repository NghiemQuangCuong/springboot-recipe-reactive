package com.cuongnghiem.springbootrecipe.controllers;

import com.cuongnghiem.springbootrecipe.command.IngredientCommand;
import com.cuongnghiem.springbootrecipe.command.RecipeCommand;
import com.cuongnghiem.springbootrecipe.command.UnitOfMeasureCommand;
import com.cuongnghiem.springbootrecipe.exception.NotFoundException;
import com.cuongnghiem.springbootrecipe.services.IngredientService;
import com.cuongnghiem.springbootrecipe.services.RecipeService;
import com.cuongnghiem.springbootrecipe.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

@Slf4j
@Controller
@RequestMapping("/recipe")
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService uomService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService uomService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.uomService = uomService;
    }

    @GetMapping("/{id}/ingredient/show")
    public String showIngredients(@PathVariable String id, Model model) {
        RecipeCommand recipeCommand = recipeService.getRecipeCommandById(id);
        if (recipeCommand != null) {
            model.addAttribute("recipe", recipeCommand);
            return "recipe/ingredient/show";
        }
        throw new NotFoundException("Recipe not found, id = " + id);
    }

    @GetMapping("/{recipeId}/ingredient/{ingredientId}/show")
    public String viewIngredient(@PathVariable String recipeId,
                                 @PathVariable String ingredientId,
                                 Model model) {
        Mono<IngredientCommand> ingredientCommand =
                ingredientService.findCommandByIdWithRecipeId(ingredientId, recipeId);
        if (ingredientCommand != null) {
            model.addAttribute("ingredient", ingredientCommand.block());
            return "recipe/ingredient/view";
        }
        throw new NotFoundException("Ingredient with Recipe not found. ingredientId = "
                + ingredientId + ", recipeId = " + recipeId);
    }

    @GetMapping("/{recipeId}/ingredient/{ingredientId}/update")
    public String getIngredientUpdate(@PathVariable String recipeId,
                                      @PathVariable String ingredientId,
                                      Model model) {
        Mono<IngredientCommand> ingredientCommand
                = ingredientService.findCommandByIdWithRecipeId(ingredientId, recipeId);
        if (ingredientCommand != null) {
            Flux<UnitOfMeasureCommand> listUOM = uomService.getAllUoMCommand();

            model.addAttribute("ingredient", ingredientCommand.block());
            model.addAttribute("listUOM", listUOM.collectList().block());

            return "recipe/ingredient/new_or_update";
        }
        throw new NotFoundException("Ingredient with Recipe not found. ingredientId = "
                + ingredientId + ", recipeId = " + recipeId);
    }

    @GetMapping("/{recipeId}/ingredient/new")
    public String newIngredient(@PathVariable String recipeId, Model model) {
        IngredientCommand ingredientCommand = new IngredientCommand();
        if (recipeService.getRecipeById(recipeId) == null)
            throw new NotFoundException("Recipe not found, id = " + recipeId);

        ingredientCommand.setRecipeId(recipeId);

        Flux<UnitOfMeasureCommand> listUOM = uomService.getAllUoMCommand();

        model.addAttribute("ingredient", ingredientCommand);
        model.addAttribute("listUOM", listUOM.collectList().block());

        return "recipe/ingredient/new_or_update";
    }

    @PostMapping("/ingredient/update")
    public String saveOrUpdate(@ModelAttribute IngredientCommand ingredientCommand) {
        IngredientCommand savedIngredientCommand
                = ingredientService.saveIngredientCommand(ingredientCommand).block();

        if (savedIngredientCommand != null)
            return "redirect:/recipe/" +
                savedIngredientCommand.getRecipeId() + "/ingredient/" +
                savedIngredientCommand.getId() + "/show";

        return "404";
    }

    @PostMapping("/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteIngredient(@PathVariable String recipeId,
                                   @PathVariable String ingredientId) {
        ingredientService.deleteByIdAndRecipeId(ingredientId, recipeId);
        return "redirect:/recipe/" + recipeId + "/ingredient/show";
    }
}
