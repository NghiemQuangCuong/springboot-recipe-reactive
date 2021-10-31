package com.cuongnghiem.springbootrecipe.command;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IngredientCommand {

    private String id;
    private String description;
    private BigDecimal amount;
    private UnitOfMeasureCommand uom;
    private String recipeId;

    public String toIngredientString() {
        String amountString;
        if (amount == null)
            amountString = "";
        else if (amount.stripTrailingZeros().scale() <= 0)
            amountString = String.valueOf(amount.intValue());
        else
            amountString = String.valueOf(amount.floatValue());

        String uomString = (uom == null) ? "" : uom.getDescription();
        String descriptionString = (description == null) ? "" : description;

        return amountString + " " +
                uomString + " " +
                descriptionString;

    }
}
