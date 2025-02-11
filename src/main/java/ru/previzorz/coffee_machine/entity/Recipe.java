package ru.previzorz.coffee_machine.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<IngredientProportion> ingredientProportions;

    public Recipe() {

    }

    public Recipe(List<IngredientProportion> ingredientProportions) {
        this.ingredientProportions = ingredientProportions;
    }

    public Long getId() {
        return id;
    }

    public List<IngredientProportion> getIngredientProportions() {
        return ingredientProportions;
    }

    public void setIngredientProportions(List<IngredientProportion> ingredientProportions) {
        this.ingredientProportions = ingredientProportions;
    }
}
