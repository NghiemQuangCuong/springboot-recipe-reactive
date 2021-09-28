package com.cuongnghiem.springbootrecipe.repositories;

import com.cuongnghiem.springbootrecipe.model.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Created by cuongnghiem on 27/09/2021
 **/

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> findByDescription(String description);
}
