package ru.previzorz.coffee_machine.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.previzorz.coffee_machine.dto.CoffeeDTO;
import ru.previzorz.coffee_machine.dto.IngredientProportionDTO;
import ru.previzorz.coffee_machine.dto.RecipeDTO;
import ru.previzorz.coffee_machine.entity.Coffee;
import ru.previzorz.coffee_machine.entity.Ingredient;
import ru.previzorz.coffee_machine.entity.IngredientProportion;
import ru.previzorz.coffee_machine.entity.Recipe;
import ru.previzorz.coffee_machine.repository.CoffeeRepository;
import ru.previzorz.coffee_machine.repository.IngredientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    private final CoffeeRepository coffeeRepository;
    final IngredientRepository ingredientRepository;

    public RecipeService(CoffeeRepository coffeeRepository, IngredientRepository ingredientRepository) {
        this.coffeeRepository = coffeeRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Transactional
    public Coffee addNewRecipe(CoffeeDTO coffeeDTO) {
        validateDoesCoffeeExist(coffeeDTO.getName());

        Set<String> ingredientNames = extractIngredientNames(coffeeDTO.getRecipe());
        Map<String, Ingredient> ingredientMap = fetchExistingIngredients(ingredientNames);

        Recipe recipe = createRecipeWithProportions(coffeeDTO.getRecipe(), ingredientMap);

        Coffee coffee = new Coffee();
        coffee.setName(coffeeDTO.getName().toLowerCase());
        coffee.setRecipe(recipe);

        return coffeeRepository.save(coffee);
    }

    private void validateDoesCoffeeExist(String name) {
        coffeeRepository.findByName(name.toLowerCase())
                .ifPresent(c -> {
                    throw new EntityExistsException("Рецепт для " + name + " уже создан");
                });
    }

    private Set<String> extractIngredientNames(RecipeDTO recipeDTO) {
        return recipeDTO.getIngredientProportions().stream()
                .map(proportionDTO -> proportionDTO.getIngredient().getName())
                .collect(Collectors.toSet());
    }

    private Map<String, Ingredient> fetchExistingIngredients(Set<String> ingredientNames) {
        return ingredientRepository.findByName_In(ingredientNames).stream()
                .collect(Collectors.toMap(Ingredient::getName, ingredient -> ingredient));
    }

    private Recipe createRecipeWithProportions(RecipeDTO recipeDTO, Map<String, Ingredient> ingredientMap) {
        Recipe recipe = new Recipe();

        List<IngredientProportion> ingredientProportions = new ArrayList<>();

        for (IngredientProportionDTO proportionDTO : recipeDTO.getIngredientProportions()) {
            Ingredient ingredient = ingredientMap.get(proportionDTO.getIngredient().getName());
            if (ingredient == null) {
                throw new EntityNotFoundException("Ингредиент " + proportionDTO.getIngredient().getName() + " не найден");
            }

            IngredientProportion ingredientProportion = new IngredientProportion();
            ingredientProportion.setIngredient(ingredient);
            ingredientProportion.setProportion(proportionDTO.getProportion());
            ingredientProportion.setRecipe(recipe);

            ingredientProportions.add(ingredientProportion);
        }

        recipe.setIngredientProportions(ingredientProportions);

        return recipe;
    }
}
