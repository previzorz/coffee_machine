package ru.previzorz.coffee_machine.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueIngredientsValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueIngredients {
    String message() default "Ингредиенты в рецепте не должны повторяться";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
