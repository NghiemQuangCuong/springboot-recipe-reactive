package com.cuongnghiem.springbootrecipe.repositories.reactive;

import com.cuongnghiem.springbootrecipe.model.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Created by cuongnghiem on 02/11/2021
 **/

public interface CategoryReactiveRepository extends ReactiveMongoRepository<Category, String> {
}
