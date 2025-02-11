package ru.previzorz.coffee_machine.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.previzorz.coffee_machine.dto.MostPopularCoffeeContentDTO;
import ru.previzorz.coffee_machine.entity.CoffeeOrderStatistic;

@Mapper(componentModel = "spring")
public interface CoffeeOrderStatisticMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "totalOrders", target = "totalOrders")
    MostPopularCoffeeContentDTO toMostPopularCoffeeContentDTO(CoffeeOrderStatistic statistic);
}
