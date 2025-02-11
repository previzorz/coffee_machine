package ru.previzorz.coffee_machine.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.previzorz.coffee_machine.dto.RecipeDTO;
import ru.previzorz.coffee_machine.entity.Recipe;

@Mapper(componentModel = "spring", uses = IngredientProportionMapper.class)
public interface RecipeMapper {

    @Mapping(target = "ingredientProportions", source = "ingredientProportions")
    Recipe recipeDTOToRecipe(RecipeDTO recipeDTO);
}
