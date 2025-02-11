package ru.previzorz.coffee_machine.service;

import org.springframework.stereotype.Service;
import ru.previzorz.coffee_machine.entity.IngredientProportion;
import ru.previzorz.coffee_machine.entity.Recipe;

import java.util.List;

@Service
public class CoffeePreparationService {

    private final IngredientQuantityService ingredientQuantityService;

    public CoffeePreparationService(IngredientQuantityService ingredientQuantityService) {
        this.ingredientQuantityService = ingredientQuantityService;
    }

    public boolean prepareCoffee(Recipe recipe) {
        List<IngredientProportion> proportions = recipe.getIngredientProportions();

        return ingredientQuantityService.getIngredients(proportions);
    }
}
