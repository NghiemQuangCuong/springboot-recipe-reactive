package com.cuongnghiem.springbootrecipe.services;


import com.cuongnghiem.springbootrecipe.command.RecipeCommand;
import com.cuongnghiem.springbootrecipe.converters.CategoryCommandToCategory;
import com.cuongnghiem.springbootrecipe.converters.NotesCommandToNotes;
import com.cuongnghiem.springbootrecipe.converters.RecipeCommandToRecipe;
import com.cuongnghiem.springbootrecipe.converters.RecipeToRecipeCommand;
import com.cuongnghiem.springbootrecipe.exception.NotFoundException;
import com.cuongnghiem.springbootrecipe.model.Recipe;
import com.cuongnghiem.springbootrecipe.repositories.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by jt on 6/13/17.
 */
@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeReactiveRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;
    private final NotesCommandToNotes notesCommandToNotes;
    private final CategoryCommandToCategory categoryCommandToCategory;

    public RecipeServiceImpl(RecipeReactiveRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand, NotesCommandToNotes notesCommandToNotes, CategoryCommandToCategory categoryCommandToCategory) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
        this.notesCommandToNotes = notesCommandToNotes;
        this.categoryCommandToCategory = categoryCommandToCategory;
    }

    @Override
    public Mono<Recipe> save(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Override
    @Transactional
    public Flux<Recipe> getRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    @Transactional
    public Mono<Recipe> getRecipeById(String id) {
        return recipeRepository.findById(id);
    }

    @Override
    @Transactional
    public Mono<RecipeCommand> getRecipeCommandById(String id) {
        return recipeRepository.findById(id)
                .map(recipeToRecipeCommand::convert);
    }

    @Override
    @Transactional
    public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command) {
        if (command == null)
            return null;

        Recipe availableRecipe = recipeRepository
                .findById(command.getId())
                .block();

        if (availableRecipe == null) {
            return recipeRepository.save(recipeCommandToRecipe.convert(command))
                    .map(recipeToRecipeCommand::convert);
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
        availableRecipe.setCategories(
                command.getCategories()
                        .stream()
                        .map(categoryCommandToCategory::convert)
                        .collect(Collectors.toSet())
        );

        return recipeRepository.save(availableRecipe)
                .map(recipeToRecipeCommand::convert);
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        recipeRepository.deleteById(id).block();
    }
}
