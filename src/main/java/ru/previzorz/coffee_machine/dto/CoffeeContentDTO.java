package ru.previzorz.coffee_machine.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class CoffeeContentDTO {

    private String name;
    private List<IngredientProportionDTO> ingredientProportions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IngredientProportionDTO> getIngredientProportions() {
        return ingredientProportions;
    }

    public void setIngredientProportions(List<IngredientProportionDTO> ingredientProportions) {
        this.ingredientProportions = ingredientProportions;
    }
}
