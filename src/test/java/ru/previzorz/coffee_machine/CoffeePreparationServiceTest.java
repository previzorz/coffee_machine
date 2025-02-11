package ru.previzorz.coffee_machine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.previzorz.coffee_machine.entity.Ingredient;
import ru.previzorz.coffee_machine.entity.IngredientProportion;
import ru.previzorz.coffee_machine.entity.Recipe;
import ru.previzorz.coffee_machine.service.CoffeePreparationService;
import ru.previzorz.coffee_machine.service.IngredientQuantityService;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CoffeePreparationServiceTest {

    @Mock
    private IngredientQuantityService ingredientQuantityService;

    private CoffeePreparationService coffeePreparationService;

    @BeforeEach
    void setUp() {
        coffeePreparationService = new CoffeePreparationService(ingredientQuantityService);
    }

    @Test
    void testPrepareCoffee_success() {
        Recipe recipe = new Recipe();

        IngredientProportion ingredientProportion1 = new IngredientProportion();
        IngredientProportion ingredientProportion2 = new IngredientProportion();

        Ingredient ingredient1 = new Ingredient();
        Ingredient ingredient2 = new Ingredient();
        ingredient1.setName("вода");
        ingredient2.setName("молоко");

        ingredientProportion1.setIngredient(ingredient1);
        ingredientProportion2.setIngredient(ingredient2);
        ingredientProportion1.setProportion(50);
        ingredientProportion2.setProportion(200);

        recipe.setIngredientProportions(Arrays.asList(ingredientProportion1, ingredientProportion2));

        when(ingredientQuantityService.getIngredients(recipe.getIngredientProportions())).thenReturn(true);

        boolean result = coffeePreparationService.prepareCoffee(recipe);

        assertTrue(result);
    }

    @Test
    void testPrepareCoffee_failed() {
        Recipe recipe = new Recipe();

        IngredientProportion ingredientProportion1 = new IngredientProportion();
        IngredientProportion ingredientProportion2 = new IngredientProportion();

        Ingredient ingredient1 = new Ingredient();
        Ingredient ingredient2 = new Ingredient();
        ingredient1.setName("вода");
        ingredient2.setName("молоко");

        ingredientProportion1.setIngredient(ingredient1);
        ingredientProportion2.setIngredient(ingredient2);
        ingredientProportion1.setProportion(50);
        ingredientProportion2.setProportion(200);

        recipe.setIngredientProportions(Arrays.asList(ingredientProportion1, ingredientProportion2));

        when(ingredientQuantityService.getIngredients(recipe.getIngredientProportions())).thenReturn(false);

        boolean result = coffeePreparationService.prepareCoffee(recipe);

        assertFalse(result);
    }

}
