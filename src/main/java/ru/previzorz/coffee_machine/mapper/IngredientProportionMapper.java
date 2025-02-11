package ru.previzorz.coffee_machine.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.previzorz.coffee_machine.dto.IngredientProportionDTO;
import ru.previzorz.coffee_machine.entity.IngredientProportion;

@Mapper(componentModel = "spring")
public interface IngredientProportionMapper {

    @Mapping(target = "ingredient.name", source = "ingredient.name")
    @Mapping(target = "proportion", source = "proportion")
    IngredientProportion ingredientProportionDTOToIngredientProportion(IngredientProportionDTO ingredientProportionDTO);
}
