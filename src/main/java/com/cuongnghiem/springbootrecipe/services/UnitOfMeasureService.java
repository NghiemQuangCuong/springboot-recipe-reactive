package com.cuongnghiem.springbootrecipe.services;

import com.cuongnghiem.springbootrecipe.command.UnitOfMeasureCommand;
import com.cuongnghiem.springbootrecipe.model.UnitOfMeasure;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

public interface UnitOfMeasureService {
    Mono<UnitOfMeasure> getUOMById(String id);
    Flux<UnitOfMeasureCommand> getAllUoMCommand();
}
