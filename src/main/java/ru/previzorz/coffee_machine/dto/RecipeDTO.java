package ru.previzorz.coffee_machine.dto;

import jakarta.validation.constraints.NotNull;
import ru.previzorz.coffee_machine.validation.UniqueIngredients;

import java.util.List;

public class RecipeDTO {

    @NotNull
    @UniqueIngredients
    private List<IngredientProportionDTO> ingredientProportions;

    public List<IngredientProportionDTO> getIngredientProportions() {
        return ingredientProportions;
    }

    public void setIngredientProportions(List<IngredientProportionDTO> ingredientProportions) {
        this.ingredientProportions = ingredientProportions;
    }
}
