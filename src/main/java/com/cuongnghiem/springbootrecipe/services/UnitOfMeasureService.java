package com.cuongnghiem.springbootrecipe.services;

import com.cuongnghiem.springbootrecipe.command.UnitOfMeasureCommand;
import com.cuongnghiem.springbootrecipe.model.UnitOfMeasure;

import java.util.Set;

public interface UnitOfMeasureService {
    UnitOfMeasure getUOMById(Long id);
    Set<UnitOfMeasureCommand> getAllUoMCommand();
}
