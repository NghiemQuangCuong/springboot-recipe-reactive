package com.cuongnghiem.springbootrecipe.converters;

import com.cuongnghiem.springbootrecipe.command.NotesCommand;
import com.cuongnghiem.springbootrecipe.model.Notes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NotesToNotesCommandTest {

    private static final String ID = "1L";
    private static final String RECIPE_NOTES = "Recipenotes";
    NotesToNotesCommand notesToNotesCommand;

    @BeforeEach
    void setUp() {
        notesToNotesCommand = new NotesToNotesCommand();
    }

    @Test
    void convert() {
        Notes notes = new Notes();
        notes.setId(ID);
        notes.setRecipeNotes(RECIPE_NOTES);

        NotesCommand notesCommand = notesToNotesCommand.convert(notes);

        assertEquals(ID, notesCommand.getId());
        assertEquals(RECIPE_NOTES, notesCommand.getRecipeNotes());
    }
}