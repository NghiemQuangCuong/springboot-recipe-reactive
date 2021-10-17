package com.cuongnghiem.springbootrecipe.services;


import com.cuongnghiem.springbootrecipe.command.RecipeCommand;
import com.cuongnghiem.springbootrecipe.converters.CategoryCommandToCategory;
import com.cuongnghiem.springbootrecipe.converters.NotesCommandToNotes;
import com.cuongnghiem.springbootrecipe.converters.RecipeCommandToRecipe;
import com.cuongnghiem.springbootrecipe.converters.RecipeToRecipeCommand;
import com.cuongnghiem.springbootrecipe.model.Recipe;
import com.cuongnghiem.springbootrecipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by jt on 6/13/17.
 */
@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;
    private final NotesCommandToNotes notesCommandToNotes;
    private final CategoryCommandToCategory categoryCommandToCategory;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand, NotesCommandToNotes notesCommandToNotes, CategoryCommandToCategory categoryCommandToCategory) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
        this.notesCommandToNotes = notesCommandToNotes;
        this.categoryCommandToCategory = categoryCommandToCategory;
    }

    @Override
    public Recipe save(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Override
    @Transactional
    public Set<Recipe> getRecipes() {
        Set<Recipe> recipeSet = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
        return recipeSet;
    }

    @Override
    @Transactional
    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public RecipeCommand getRecipeCommandById(Long id) {
        Recipe recipe = recipeRepository.findById(id).orElse(null);
        if (recipe == null)
            return null;
        return recipeToRecipeCommand.convert(recipe);
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        if (command == null)
            return null;

        Recipe availableRecipe = recipeRepository.findById(command.getId()).orElse(null);
        if (availableRecipe == null) {
            Recipe recipe = recipeRepository.save(recipeCommandToRecipe.convert(command));
            return recipeToRecipeCommand.convert(recipe);
        }

        availableRecipe.setUrl(command.getUrl());
        availableRecipe.setServings(command.getServings());
        availableRecipe.setCookTime(command.getCookTime());
        availableRecipe.setDescription(command.getDescription());
        availableRecipe.setDifficulty(command.getDifficulty());
        availableRecipe.setDirections(command.getDirections());
        availableRecipe.setPrepTime(command.getPrepTime());
        availableRecipe.setSource(command.getSource());
        availableRecipe.setNotes(notesCommandToNotes.convert(command.getNotes()));
        command.getCategories().forEach(categoryCommand -> {
            availableRecipe.getCategories().add(categoryCommandToCategory.convert(categoryCommand));
        });

        return recipeToRecipeCommand.convert(recipeRepository.save(availableRecipe));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (recipeRepository.findById(id).isPresent())
            recipeRepository.deleteById(id);
    }
}
