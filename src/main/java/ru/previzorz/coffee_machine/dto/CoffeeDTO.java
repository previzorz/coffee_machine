package ru.previzorz.coffee_machine.dto;

import jakarta.validation.constraints.NotNull;

public class CoffeeDTO {

    @NotNull
    private String name;
    @NotNull
    private RecipeDTO recipe;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RecipeDTO getRecipe() {
        return recipe;
    }

    public void setRecipe(RecipeDTO recipe) {
        this.recipe = recipe;
    }
}
