package com.cuongnghiem.springbootrecipe.services;

import com.cuongnghiem.springbootrecipe.command.UnitOfMeasureCommand;
import com.cuongnghiem.springbootrecipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.cuongnghiem.springbootrecipe.model.UnitOfMeasure;
import com.cuongnghiem.springbootrecipe.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UnitOfMeasureServiceImplTest {
    @InjectMocks
    UnitOfMeasureServiceImpl unitOfMeasureService;
    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;
    @Mock
    UnitOfMeasureToUnitOfMeasureCommand converter;

    @BeforeEach
    void setUp() {
        converter = new UnitOfMeasureToUnitOfMeasureCommand();
        unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, converter);
    }

    @Test
    void getAllUoMCommand() {
        Set<UnitOfMeasure> unitOfMeasureSet = new HashSet<>();
        UnitOfMeasure uom1 = new UnitOfMeasure(); uom1.setId("1L");
        UnitOfMeasure uom2 = new UnitOfMeasure(); uom2.setId("2L");
        UnitOfMeasure uom3 = new UnitOfMeasure(); uom3.setId("3L");
        unitOfMeasureSet.add(uom1); unitOfMeasureSet.add(uom2); unitOfMeasureSet.add(uom3);

        when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasureSet);

        Set<UnitOfMeasureCommand> result = unitOfMeasureService.getAllUoMCommand();

        assertEquals(3, result.size());
    }
}