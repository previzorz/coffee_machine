package ru.previzorz.coffee_machine.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "ingredient_quantities")
public class IngredientQuantity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    @Min(0)
    @NotNull
    private Integer quantity;

    public IngredientQuantity() {

    }

    public IngredientQuantity(Ingredient ingredient, Integer quantity) {
        this.ingredient = ingredient;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
