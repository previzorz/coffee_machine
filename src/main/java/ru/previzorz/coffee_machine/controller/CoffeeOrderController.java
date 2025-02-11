package ru.previzorz.coffee_machine.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.previzorz.coffee_machine.service.CoffeeOrderService;

@RestController
@RequestMapping("/api/orders")
public class CoffeeOrderController {

    private final CoffeeOrderService coffeeOrderService;

    public CoffeeOrderController(CoffeeOrderService coffeeOrderService) {
        this.coffeeOrderService = coffeeOrderService;
    }

    @PostMapping("/{coffeeName}")
    @Operation(summary = "Заказ кофе", description = "Позволяет пользователю заказать кофе по названию")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Кофе готов"),
            @ApiResponse(responseCode = "400", description = "Не хватает ингредиентов для приготовления кофе")
    })
    public ResponseEntity<String> orderCoffee(@PathVariable @NotBlank String coffeeName) {
        boolean isReady = coffeeOrderService.processCoffeeOrder(coffeeName);
        if (isReady) {
            return ResponseEntity.ok("Кофе готов");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Не хватает ингредиентов для приготовления " + coffeeName);
        }
    }
}
