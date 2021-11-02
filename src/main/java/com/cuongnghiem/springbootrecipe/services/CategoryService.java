package com.cuongnghiem.springbootrecipe.services;

import com.cuongnghiem.springbootrecipe.command.CategoryCommand;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

/**
 * Created by cuongnghiem on 15/10/2021
 **/

public interface CategoryService {
    Mono<CategoryCommand> findById(String id);
    Flux<CategoryCommand> getAllCategoryCommand();
}
