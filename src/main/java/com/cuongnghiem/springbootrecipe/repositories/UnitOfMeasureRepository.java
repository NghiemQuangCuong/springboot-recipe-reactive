package com.cuongnghiem.springbootrecipe.repositories;

import com.cuongnghiem.springbootrecipe.model.UnitOfMeasure;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Created by cuongnghiem on 27/09/2021
 **/

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {

    Optional<UnitOfMeasure> findByDescription(String description);
}
