package ru.previzorz.coffee_machine.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.previzorz.coffee_machine.entity.*;
import ru.previzorz.coffee_machine.repository.CoffeeRepository;

@Service
public class CoffeeOrderService {

    private final CoffeeRepository coffeeRepository;
    private final CoffeePreparationService coffeePreparationService;
    private final CoffeeOrderStatisticService coffeeOrderStatisticService;

    public CoffeeOrderService(CoffeeRepository coffeeRepository,
                              CoffeePreparationService coffeePreparationService, CoffeeOrderStatisticService coffeeOrderStatisticService) {
        this.coffeeRepository = coffeeRepository;
        this.coffeePreparationService = coffeePreparationService;
        this.coffeeOrderStatisticService = coffeeOrderStatisticService;
    }

    @Transactional
    public synchronized boolean processCoffeeOrder(String name) {
        Coffee coffee = coffeeRepository.findByName(name.toLowerCase())
                .orElseThrow(() -> new EntityNotFoundException("Рецепт для " + name + " отсутствует"));

        boolean hasReady = coffeePreparationService.prepareCoffee(coffee.getRecipe());

        if (hasReady) coffeeOrderStatisticService.increaseTotalOrders(name.toLowerCase());

        return hasReady;
    }
}
