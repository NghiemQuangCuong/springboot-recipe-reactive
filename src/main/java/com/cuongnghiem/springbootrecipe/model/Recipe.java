package com.cuongnghiem.springbootrecipe.model;

import lombok.*;

import javax.persistence.*;
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
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;

    @Lob
    private String directions;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients = new HashSet<>();

    @Lob
    private Byte[] image;

    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;

    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;

    @ManyToMany
    @JoinTable(name = "recipe_category",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    public void setNotes(Notes notes) {
        this.notes = notes;
        notes.setRecipe(this);
    }

    public Recipe addIngredient(Ingredient ingredient){
        ingredient.setRecipe(this);
        this.ingredients.add(ingredient);
        return this;
    }

    public Set<String> getIngredientStringSet() {
        Set<String> ingredientStringSet = new HashSet<>();
        ingredients.forEach(ingredient -> {

            String amount;
            if (ingredient.getAmount() == null)
                amount = "";
            else if (ingredient.getAmount().stripTrailingZeros().scale() <= 0)
                amount = String.valueOf(ingredient.getAmount().intValue());
            else
                amount = String.valueOf(ingredient.getAmount().floatValue());

            String uom = (ingredient.getUom() == null) ? "" : ingredient.getUom().getDescription();
            String description = (ingredient.getDescription() == null) ? "" : ingredient.getDescription();

            ingredientStringSet.add(new StringBuilder()
                    .append(amount).append(" ")
                    .append(uom).append(" ")
                    .append(description)
                    .toString());
        });

        return ingredientStringSet;
    }
}
