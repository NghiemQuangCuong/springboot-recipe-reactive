package com.cuongnghiem.springbootrecipe.services;

import com.cuongnghiem.springbootrecipe.command.UnitOfMeasureCommand;
import com.cuongnghiem.springbootrecipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.cuongnghiem.springbootrecipe.model.UnitOfMeasure;
import com.cuongnghiem.springbootrecipe.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService{

//    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand converter;

    public UnitOfMeasureServiceImpl(UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository, UnitOfMeasureToUnitOfMeasureCommand converter) {
        this.unitOfMeasureReactiveRepository = unitOfMeasureReactiveRepository;
        this.converter = converter;
    }


    @Override
    public Mono<UnitOfMeasure> getUOMById(String id) {
        return unitOfMeasureReactiveRepository.findById(id);
//        return unitOfMeasureRepository.findById(id).orElse(null);
    }

    @Override
    public Flux<UnitOfMeasureCommand> getAllUoMCommand() {
        return unitOfMeasureReactiveRepository.findAll()
                .map(converter::convert);

//        Set<UnitOfMeasureCommand> commandSet = new HashSet<>();
//        unitOfMeasureRepository.findAll().forEach(uom -> {
//            commandSet.add(converter.convert(uom));
//        });
//
//        return commandSet;
    }
}
