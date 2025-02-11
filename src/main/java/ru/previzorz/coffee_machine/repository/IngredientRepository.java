package ru.previzorz.coffee_machine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.previzorz.coffee_machine.entity.Ingredient;

import java.util.List;
import java.util.Set;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findByName_In(Set<String> ingredientNames);
}
