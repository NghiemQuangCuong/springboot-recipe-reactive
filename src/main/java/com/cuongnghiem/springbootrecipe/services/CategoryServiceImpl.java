package com.cuongnghiem.springbootrecipe.services;

import com.cuongnghiem.springbootrecipe.command.CategoryCommand;
import com.cuongnghiem.springbootrecipe.converters.CategoryToCategoryCommand;
import com.cuongnghiem.springbootrecipe.model.Category;
import com.cuongnghiem.springbootrecipe.repositories.reactive.CategoryReactiveRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by cuongnghiem on 15/10/2021
 **/
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryReactiveRepository categoryRepository;
    private final CategoryToCategoryCommand categoryToCategoryCommand;

    public CategoryServiceImpl(CategoryReactiveRepository categoryRepository, CategoryToCategoryCommand categoryToCategoryCommand) {
        this.categoryRepository = categoryRepository;
        this.categoryToCategoryCommand = categoryToCategoryCommand;
    }

    @Override
    public Mono<CategoryCommand> findById(String id) {
        return categoryRepository.findById(id)
                .map(categoryToCategoryCommand::convert);
    }

    @Override
    public Flux<CategoryCommand> getAllCategoryCommand() {
        return categoryRepository.findAll()
                .map(categoryToCategoryCommand::convert);
    }
}
