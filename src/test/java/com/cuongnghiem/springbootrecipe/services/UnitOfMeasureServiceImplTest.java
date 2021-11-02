package com.cuongnghiem.springbootrecipe.services;

import com.cuongnghiem.springbootrecipe.command.UnitOfMeasureCommand;
import com.cuongnghiem.springbootrecipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.cuongnghiem.springbootrecipe.model.UnitOfMeasure;
import com.cuongnghiem.springbootrecipe.repositories.UnitOfMeasureRepository;
import com.cuongnghiem.springbootrecipe.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UnitOfMeasureServiceImplTest {
    @InjectMocks
    UnitOfMeasureServiceImpl unitOfMeasureService;
    @Mock
    UnitOfMeasureReactiveRepository unitOfMeasureRepository;
    @Mock
    UnitOfMeasureToUnitOfMeasureCommand converter;

    @BeforeEach
    void setUp() {
        converter = new UnitOfMeasureToUnitOfMeasureCommand();
        unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, converter);
    }

    @Test
    void getAllUoMCommand() {
        UnitOfMeasure uom1 = new UnitOfMeasure(); uom1.setId("1L");
        UnitOfMeasure uom2 = new UnitOfMeasure(); uom2.setId("2L");
        UnitOfMeasure uom3 = new UnitOfMeasure(); uom3.setId("3L");
        Flux<UnitOfMeasure> unitOfMeasureFlux = Flux.just(uom1, uom2, uom3);

        when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasureFlux);

        Flux<UnitOfMeasureCommand> result = unitOfMeasureService.getAllUoMCommand();

        assertEquals(3, result.count().block());
    }
}