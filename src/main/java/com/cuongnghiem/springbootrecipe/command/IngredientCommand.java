package com.cuongnghiem.springbootrecipe.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {
    private Long id;
    private String description;
    private BigDecimal amount;
    private UnitOfMeasureCommand uom;
    private Long recipeId;

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
