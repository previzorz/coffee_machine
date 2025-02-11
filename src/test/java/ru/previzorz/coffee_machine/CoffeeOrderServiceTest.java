package ru.previzorz.coffee_machine;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.previzorz.coffee_machine.entity.Coffee;
import ru.previzorz.coffee_machine.entity.Recipe;
import ru.previzorz.coffee_machine.repository.CoffeeRepository;
import ru.previzorz.coffee_machine.service.CoffeeOrderService;
import ru.previzorz.coffee_machine.service.CoffeeOrderStatisticService;
import ru.previzorz.coffee_machine.service.CoffeePreparationService;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CoffeeOrderServiceTest {

    @Mock
    private CoffeeRepository coffeeRepository;

    @Mock
    private CoffeePreparationService coffeePreparationService;

    @Mock
    private CoffeeOrderStatisticService coffeeOrderStatisticService;

    private CoffeeOrderService coffeeOrderService;

    @BeforeEach
    void setUp() {
        coffeeOrderService = new CoffeeOrderService(coffeeRepository, coffeePreparationService, coffeeOrderStatisticService);
    }

    @Test
    void testProcessCoffeeOrder_success() {
        String name = "эспрессо";
        Coffee coffee = new Coffee();
        coffee.setName(name);
        coffee.setRecipe(new Recipe());

        when(coffeeRepository.findByName(name.toLowerCase())).thenReturn(Optional.of(coffee));
        when(coffeePreparationService.prepareCoffee(any())).thenReturn(true);

        boolean result = coffeeOrderService.processCoffeeOrder(name);

        assertTrue(result);
        verify(coffeeOrderStatisticService, times(1)).increaseTotalOrders(name.toLowerCase());
    }

    @Test
    void testProcessCoffeeOrder_noRecipeFound() {
        String name = "латте";

        when(coffeeRepository.findByName(name.toLowerCase())).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> coffeeOrderService.processCoffeeOrder(name));
        assertEquals("Рецепт для " + name + " отсутствует", exception.getMessage());
    }

    @Test
    void testProcessCoffeeOrder_failedPreparation() {
        String coffeeName = "американо";
        Coffee coffee = new Coffee();
        coffee.setName(coffeeName);
        coffee.setRecipe(new Recipe());

        when(coffeeRepository.findByName(coffeeName.toLowerCase())).thenReturn(Optional.of(coffee));
        when(coffeePreparationService.prepareCoffee(any())).thenReturn(false);

        boolean result = coffeeOrderService.processCoffeeOrder(coffeeName);

        assertFalse(result);
        verify(coffeeOrderStatisticService, never()).increaseTotalOrders(coffeeName.toLowerCase());
    }
}
