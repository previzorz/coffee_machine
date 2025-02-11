package ru.previzorz.coffee_machine.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.previzorz.coffee_machine.dto.CoffeeContentDTO;
import ru.previzorz.coffee_machine.dto.CoffeeDTO;
import ru.previzorz.coffee_machine.entity.Coffee;
import ru.previzorz.coffee_machine.mapper.CoffeeMapper;
import ru.previzorz.coffee_machine.service.RecipeService;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    private final CoffeeMapper coffeeMapper;

    public RecipeController(RecipeService recipeService, CoffeeMapper coffeeMapper) {
        this.recipeService = recipeService;
        this.coffeeMapper = coffeeMapper;
    }

    @PostMapping("/add")
    @Operation(summary = "Добавить новый рецепт кофе", description = "Добавляет новый рецепт кофе в систему")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Рецепт успешно добавлен"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации данных")
    })
    public ResponseEntity<?> addNewRecipe(@RequestBody @Valid CoffeeDTO coffeeDTO) {
        Coffee addedCoffee = recipeService.addNewRecipe(coffeeDTO);

        CoffeeContentDTO coffeeContentDTO = coffeeMapper.coffeeToCoffeeContentDTO(addedCoffee);

        return ResponseEntity.ok(coffeeContentDTO);
    }
}
