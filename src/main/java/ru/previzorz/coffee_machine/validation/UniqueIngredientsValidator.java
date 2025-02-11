package ru.previzorz.coffee_machine.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.previzorz.coffee_machine.dto.IngredientProportionDTO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UniqueIngredientsValidator implements ConstraintValidator<UniqueIngredients, List<IngredientProportionDTO>> {

    @Override
    public boolean isValid(List<IngredientProportionDTO> ingredientProportions, ConstraintValidatorContext context) {
        if (ingredientProportions == null) {
            return true;
        }

        Set<String> ingredientNames = new HashSet<>();
        for (IngredientProportionDTO proportion : ingredientProportions) {
            if (!ingredientNames.add(proportion.getIngredient().getName().toLowerCase())) {
                return false;
            }
        }

        return true;
    }
}
