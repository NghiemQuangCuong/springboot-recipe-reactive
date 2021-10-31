package com.cuongnghiem.springbootrecipe.command;

import com.cuongnghiem.springbootrecipe.model.Difficulty;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeCommand {
    private String id;

    @NotBlank(message = "Description must not be empty")
    @Size(min = 3, max = 255, message = "Description length must between 3 and 255")
    private String description;

    @Min(value = 1, message = "Prepare time must be bigger than 0")
    @Max(value = 999, message = "Prepare time must be smaller than 1000")
    private Integer prepTime;

    @Min(value = 1, message = "Cook time must be bigger than 0")
    @Max(value = 999, message = "Cook time must be smaller than 1000")
    private Integer cookTime;

    @Min(value = 1, message = "Servings must be bigger than 0")
    @Max(value = 100, message = "Servings must be smaller than 101")
    private Integer servings;

    private String source;

    @URL(message = "Please enter valid URL")
    private String url;

    @NotBlank(message = "Directions must not be empty")
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
