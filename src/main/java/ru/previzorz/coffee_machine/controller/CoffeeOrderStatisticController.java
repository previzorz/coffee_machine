package ru.previzorz.coffee_machine.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.previzorz.coffee_machine.dto.MostPopularCoffeeContentDTO;
import ru.previzorz.coffee_machine.entity.CoffeeOrderStatistic;
import ru.previzorz.coffee_machine.mapper.CoffeeOrderStatisticMapper;
import ru.previzorz.coffee_machine.service.CoffeeOrderStatisticService;

@RestController
@RequestMapping("/api/statistic")
public class CoffeeOrderStatisticController {

    private final CoffeeOrderStatisticService coffeeOrderStatisticService;

    private final CoffeeOrderStatisticMapper coffeeOrderStatisticMapper;

    public CoffeeOrderStatisticController(CoffeeOrderStatisticService coffeeOrderStatisticService, CoffeeOrderStatisticMapper coffeeOrderStatisticMapper) {
        this.coffeeOrderStatisticService = coffeeOrderStatisticService;
        this.coffeeOrderStatisticMapper = coffeeOrderStatisticMapper;
    }

    @GetMapping("/most-popular")
    @Operation(summary = "Получить самый популярный напиток", description = "Возвращает самый популярный напиток на основе статистики заказов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Самый популярный напиток найден и возвращён"),
            @ApiResponse(responseCode = "404", description = "Самый популярный напиток не найден")
    })
    public ResponseEntity<MostPopularCoffeeContentDTO> getMostPopularCoffee() {
        CoffeeOrderStatistic mostPopularCoffee = coffeeOrderStatisticService.getMostPopularCoffee();

        MostPopularCoffeeContentDTO mostPopularCoffeeContentDTO = coffeeOrderStatisticMapper.toMostPopularCoffeeContentDTO(mostPopularCoffee);

        return ResponseEntity.ok(mostPopularCoffeeContentDTO);
    }
}
