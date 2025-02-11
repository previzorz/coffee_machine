package ru.previzorz.coffee_machine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.previzorz.coffee_machine.entity.IngredientQuantity;

import java.util.List;
import java.util.Set;

@Repository
public interface IngredientQuantityRepository extends JpaRepository<IngredientQuantity, Long> {

    List<IngredientQuantity> findByIngredient_IdIn(Set<Long> ingredientIds);
}
