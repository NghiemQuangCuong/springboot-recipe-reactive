package com.cuongnghiem.springbootrecipe.controllers;

import com.cuongnghiem.springbootrecipe.command.RecipeCommand;
import com.cuongnghiem.springbootrecipe.converters.RecipeToRecipeCommand;
import com.cuongnghiem.springbootrecipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by jt on 6/1/17.
 */
@Slf4j
@Controller
public class IndexController {

    private final RecipeService recipeService;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public IndexController(RecipeService recipeService, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeService = recipeService;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {
        log.debug("Getting Index page");

        model.addAttribute("recipes", recipeService.getRecipes()
                .map(recipeToRecipeCommand::convert)
                .collectList()
                .block());

        return "index";
    }
}
