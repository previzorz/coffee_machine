package ru.previzorz.coffee_machine.dto;

import jakarta.validation.constraints.NotNull;

public class IngredientProportionDTO {

    @NotNull
    private IngredientDTO ingredient;

    @NotNull
    private Integer proportion;

    public IngredientDTO getIngredient() {
        return ingredient;
    }

    public void setIngredient(IngredientDTO ingredient) {
        this.ingredient = ingredient;
    }

    public Integer getProportion() {
        return proportion;
    }

    public void setProportion(Integer proportion) {
        this.proportion = proportion;
    }
}
