package ru.previzorz.coffee_machine.service;

import org.springframework.stereotype.Service;
import ru.previzorz.coffee_machine.entity.Ingredient;
import ru.previzorz.coffee_machine.entity.IngredientProportion;
import ru.previzorz.coffee_machine.entity.IngredientQuantity;
import ru.previzorz.coffee_machine.repository.IngredientQuantityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class IngredientQuantityService {

    private final IngredientQuantityRepository ingredientQuantityRepository;


    public IngredientQuantityService(IngredientQuantityRepository ingredientQuantityRepository) {
        this.ingredientQuantityRepository = ingredientQuantityRepository;
    }

    public boolean getIngredients(List<IngredientProportion> proportions) {
        Set<Long> ingredientIds = getIngredientsIds(proportions);
        Map<Long, Integer> ingredientQuantityMap = getIngredientsQuantities(ingredientIds);

        for (IngredientProportion proportion : proportions) {
            Ingredient ingredient = proportion.getIngredient();
            int requiredAmount = proportion.getProportion();

            Integer availableAmount = ingredientQuantityMap.get(ingredient.getId());
            if (availableAmount == null || availableAmount < requiredAmount) {
                return false;
            }
        }

        decreaseIngredientsQuantities(proportions, ingredientQuantityMap);

        return true;
    }

    private Set<Long> getIngredientsIds(List<IngredientProportion> proportions) {
        return proportions.stream()
                .map(proportion -> proportion.getIngredient().getId())
                .collect(Collectors.toSet());
    }

    private Map<Long, Integer> getIngredientsQuantities(Set<Long> ingredientIds) {
        List<IngredientQuantity> quantities = ingredientQuantityRepository.findByIngredient_IdIn(ingredientIds);

        return quantities.stream()
                .collect(Collectors.toMap(
                        ingredientQuantity -> ingredientQuantity.getIngredient().getId(),
                        IngredientQuantity::getQuantity));
    }

    protected void decreaseIngredientsQuantities(List<IngredientProportion> proportions,
                                                 Map<Long, Integer> ingredientQuantityMap) {

        List<IngredientQuantity> updatedQuantities = new ArrayList<>();

        for (IngredientProportion proportion : proportions) {
            Ingredient ingredient = proportion.getIngredient();
            int requiredAmount = proportion.getProportion();

            Integer availableAmount = ingredientQuantityMap.get(ingredient.getId());
            if (availableAmount != null) {

                int updatedAmount = availableAmount - requiredAmount;

                ingredientQuantityMap.put(ingredient.getId(), updatedAmount);

                IngredientQuantity ingredientQuantity = new IngredientQuantity();
                ingredientQuantity.setIngredient(ingredient);
                ingredientQuantity.setQuantity(updatedAmount);
                updatedQuantities.add(ingredientQuantity);
            }
        }

        ingredientQuantityRepository.saveAll(updatedQuantities);
    }
}
