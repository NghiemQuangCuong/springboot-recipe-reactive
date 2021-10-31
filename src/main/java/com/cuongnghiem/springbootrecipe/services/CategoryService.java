package com.cuongnghiem.springbootrecipe.services;

import com.cuongnghiem.springbootrecipe.command.CategoryCommand;

import java.util.Set;

/**
 * Created by cuongnghiem on 15/10/2021
 **/

public interface CategoryService {
    CategoryCommand findById(String id);
    Set<CategoryCommand> getAllCategoryCommand();
}
