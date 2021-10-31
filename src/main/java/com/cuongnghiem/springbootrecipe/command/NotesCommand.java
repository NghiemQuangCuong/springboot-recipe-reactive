package com.cuongnghiem.springbootrecipe.command;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotesCommand {
    private String id;
    private String recipeNotes;
}
