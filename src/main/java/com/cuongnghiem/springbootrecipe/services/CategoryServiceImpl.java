package com.cuongnghiem.springbootrecipe.services;

import com.cuongnghiem.springbootrecipe.command.CategoryCommand;
import com.cuongnghiem.springbootrecipe.converters.CategoryToCategoryCommand;
import com.cuongnghiem.springbootrecipe.model.Category;
import com.cuongnghiem.springbootrecipe.repositories.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by cuongnghiem on 15/10/2021
 **/
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryToCategoryCommand categoryToCategoryCommand;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryToCategoryCommand categoryToCategoryCommand) {
        this.categoryRepository = categoryRepository;
        this.categoryToCategoryCommand = categoryToCategoryCommand;
    }

    @Override
    public CategoryCommand findById(String id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null)
            throw new RuntimeException("Cannot find category id: " + id);
        return categoryToCategoryCommand.convert(category);
    }

    @Override
    public Set<CategoryCommand> getAllCategoryCommand() {
        Set<CategoryCommand> categoryCommandSet = new HashSet<>();
        categoryRepository.findAll().forEach(category -> {
            categoryCommandSet.add(categoryToCategoryCommand.convert(category));
        });
        return categoryCommandSet;
    }
}
