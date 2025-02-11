package ru.previzorz.coffee_machine.dto;

import jakarta.validation.constraints.NotNull;

public class IngredientDTO {

    @NotNull
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
