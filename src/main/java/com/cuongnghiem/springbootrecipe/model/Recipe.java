package com.cuongnghiem.springbootrecipe.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by cuongnghiem on 25/09/2021
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class Recipe {

    @Id
    private String id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private Set<Ingredient> ingredients = new HashSet<>();
    private byte[] image;
    private Difficulty difficulty;
    private Notes notes;
    @DBRef
    private Set<Category> categories = new HashSet<>();

    public void setNotes(Notes notes) {
        this.notes = notes;
    }

    public Recipe addIngredient(Ingredient ingredient){
        if (ingredient != null) {
            this.ingredients.add(ingredient);
            ingredient.setRecipe(this);
        }
        return this;
    }
}
