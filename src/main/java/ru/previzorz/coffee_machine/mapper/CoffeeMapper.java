package ru.previzorz.coffee_machine.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.previzorz.coffee_machine.dto.CoffeeContentDTO;
import ru.previzorz.coffee_machine.entity.Coffee;

@Mapper(componentModel = "spring")
public interface CoffeeMapper {

    @Mapping(source = "recipe.ingredientProportions", target = "ingredientProportions")
    CoffeeContentDTO coffeeToCoffeeContentDTO(Coffee coffee);

}
