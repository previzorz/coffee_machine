package ru.previzorz.coffee_machine.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "coffees")
public class Coffee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true, nullable = false)
    private String name;

    @ManyToOne
    @NotNull
    private Recipe recipe;

    @PrePersist
    @PreUpdate
    private void normalizeName() {
        this.name = this.name.toLowerCase();
    }

    public Coffee() {

    }

    public Coffee(String name, Recipe recipe) {
        this.name = name;
        this.recipe = recipe;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
