package ru.previzorz.coffee_machine.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(
        name = "ingredient_proportions",
        uniqueConstraints = @UniqueConstraint(columnNames = {"recipe_id", "ingredient_id"})
)
public class IngredientProportion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Min(1)
    @NotNull
    @Column(unique = true, nullable = false)
    private Integer proportion;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    public IngredientProportion() {

    }

    public IngredientProportion(Integer proportion, Recipe recipe, Ingredient ingredient) {
        this.proportion = proportion;
        this.recipe = recipe;
        this.ingredient = ingredient;
    }

    public Long getId() {
        return id;
    }

    public Integer getProportion() {
        return proportion;
    }

    public void setProportion(Integer proportion) {
        this.proportion = proportion;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }
}
