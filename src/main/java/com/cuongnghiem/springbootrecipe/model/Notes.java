package com.cuongnghiem.springbootrecipe.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by cuongnghiem on 25/09/2021
 **/

@Getter
@Setter
public class Notes {

    private String id;
    private Recipe recipe;
    private String recipeNotes;

}