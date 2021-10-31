package com.cuongnghiem.springbootrecipe.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by cuongnghiem on 25/09/2021
 **/

@Getter
@Setter
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Notes {
    @Id
    private String id;
    @DBRef
    private Recipe recipe;
    private String recipeNotes;
}