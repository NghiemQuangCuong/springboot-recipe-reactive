package com.cuongnghiem.springbootrecipe.converters;

import com.cuongnghiem.springbootrecipe.command.UnitOfMeasureCommand;
import com.cuongnghiem.springbootrecipe.model.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UnitOfMeasureCommandToUnitOfMeasureTest {

    private static final String ID = "1L";
    private static final String DESCRIPTION = "Description";

    UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;

    @BeforeEach
    void setUp() {
        unitOfMeasureCommandToUnitOfMeasure = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    void convert() {
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(ID);
        unitOfMeasureCommand.setDescription(DESCRIPTION);

        UnitOfMeasure unitOfMeasure = unitOfMeasureCommandToUnitOfMeasure.convert(unitOfMeasureCommand);

        assertEquals(ID, unitOfMeasure.getId());
        assertEquals(DESCRIPTION, unitOfMeasure.getDescription());
    }
}