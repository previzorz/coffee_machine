package ru.previzorz.coffee_machine.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "ingredients")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotNull
    @Column(unique = true, nullable = false)
    private String name;

    @PrePersist
    @PreUpdate
    private void normalizeName() {
        this.name = this.name.toLowerCase();
    }

    public Ingredient() {

    }

    public Ingredient(String name) {
        this.name = name;
    }

    public Long getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
