package ru.previzorz.coffee_machine;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.previzorz.coffee_machine.entity.CoffeeOrderStatistic;
import ru.previzorz.coffee_machine.repository.CoffeeOrderStatisticRepository;
import ru.previzorz.coffee_machine.service.CoffeeOrderStatisticService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CoffeeOrderStatisticServiceTest {

    @Mock
    private CoffeeOrderStatisticRepository coffeeOrderStatisticRepository;

    @InjectMocks
    private CoffeeOrderStatisticService coffeeOrderStatisticService;

    @Test
    void increaseTotalOrders_ExistingStatistic_ShouldIncreaseOrders() {
        String name = "латте";
        CoffeeOrderStatistic existingStatistic = new CoffeeOrderStatistic();
        existingStatistic.setName(name);
        existingStatistic.setTotalOrders(5);

        when(coffeeOrderStatisticRepository.findByName(name))
                .thenReturn(Optional.of(existingStatistic));

        coffeeOrderStatisticService.increaseTotalOrders(name);

        assertEquals(6, existingStatistic.getTotalOrders());
        verify(coffeeOrderStatisticRepository, times(1)).findByName(name);
        verify(coffeeOrderStatisticRepository, never()).save(any());
    }

    @Test
    void increaseTotalOrders_NewStatistic_ShouldCreateNewStatistic() {
        String name = "капучино";

        when(coffeeOrderStatisticRepository.findByName(name))
                .thenReturn(Optional.empty());

        coffeeOrderStatisticService.increaseTotalOrders(name);

        verify(coffeeOrderStatisticRepository, times(1)).save(any(CoffeeOrderStatistic.class));
    }

    @Test
    void getMostPopularCoffee_ShouldReturnMostPopular() {
        CoffeeOrderStatistic statistic1 = new CoffeeOrderStatistic();
        statistic1.setName("латте");
        statistic1.setTotalOrders(10);

        CoffeeOrderStatistic statistic2 = new CoffeeOrderStatistic();
        statistic2.setName("капучино");
        statistic2.setTotalOrders(20);

        when(coffeeOrderStatisticRepository.findAll())
                .thenReturn(List.of(statistic1, statistic2));

        CoffeeOrderStatistic mostPopular = coffeeOrderStatisticService.getMostPopularCoffee();

        assertEquals("капучино", mostPopular.getName());
    }

    @Test
    void getMostPopularCoffee_ShouldThrowExceptionIfNoStatistics() {
        when(coffeeOrderStatisticRepository.findAll())
                .thenReturn(Collections.emptyList());

        assertThrows(EntityNotFoundException.class,
                () -> coffeeOrderStatisticService.getMostPopularCoffee());
    }
}
