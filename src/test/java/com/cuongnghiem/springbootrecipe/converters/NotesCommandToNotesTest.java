package com.cuongnghiem.springbootrecipe.converters;

import com.cuongnghiem.springbootrecipe.command.NotesCommand;
import com.cuongnghiem.springbootrecipe.model.Notes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NotesCommandToNotesTest {

    private static final Long ID = 1L;
    private static final String RECIPE_NOTES = "recipeNotes";

    NotesCommandToNotes notesCommandToNotes;

    @BeforeEach
    void setUp() {
        notesCommandToNotes = new NotesCommandToNotes();
    }

    @Test
    void convert() {
        // given
        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(ID);
        notesCommand.setRecipeNotes(RECIPE_NOTES);

        // when
        Notes notes = notesCommandToNotes.convert(notesCommand);

        // then
        assertEquals(ID, notes.getId());
        assertEquals(RECIPE_NOTES, notes.getRecipeNotes());
    }
}