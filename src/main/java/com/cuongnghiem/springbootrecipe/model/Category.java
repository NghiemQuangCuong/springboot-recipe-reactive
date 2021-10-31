package com.cuongnghiem.springbootrecipe.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by cuongnghiem on 27/09/2021
 **/
@Getter
@Setter
public class Category {
    private String id;
    private String description;
    private Set<Recipe> recipes = new HashSet<>();
}
