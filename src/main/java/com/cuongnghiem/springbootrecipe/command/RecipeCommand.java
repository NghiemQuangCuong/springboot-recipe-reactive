package com.cuongnghiem.springbootrecipe.command;

import com.cuongnghiem.springbootrecipe.model.Difficulty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {
    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private Set<IngredientCommand> ingredients = new HashSet<>();
    private byte[] image;
    private Difficulty difficulty;
    private NotesCommand notes;
    private Set<CategoryCommand> categories = new HashSet<>();

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
