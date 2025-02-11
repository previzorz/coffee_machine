package ru.previzorz.coffee_machine.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.previzorz.coffee_machine.entity.CoffeeOrderStatistic;
import ru.previzorz.coffee_machine.repository.CoffeeOrderStatisticRepository;

import java.time.LocalDateTime;
import java.util.Comparator;

@Service
public class CoffeeOrderStatisticService {

    private final CoffeeOrderStatisticRepository coffeeOrderStatisticRepository;

    public CoffeeOrderStatisticService(CoffeeOrderStatisticRepository coffeeOrderStatisticRepository) {
        this.coffeeOrderStatisticRepository = coffeeOrderStatisticRepository;
    }

    public void increaseTotalOrders(String name) {
        coffeeOrderStatisticRepository.findByName(name)
                .ifPresentOrElse(
                        statistic -> statistic.setTotalOrders(statistic.getTotalOrders() + 1),
                        () -> createNewStatistic(name)
                );
    }

    private void createNewStatistic(String name) {
        CoffeeOrderStatistic statistic = new CoffeeOrderStatistic();
        statistic.setName(name);
        statistic.setTotalOrders(1);
        statistic.setFirstOrderDate(LocalDateTime.now());

        coffeeOrderStatisticRepository.save(statistic);
    }

    @Transactional(readOnly = true)
    public CoffeeOrderStatistic getMostPopularCoffee() {
        return coffeeOrderStatisticRepository.findAll().stream()
                .max(Comparator.comparingInt(CoffeeOrderStatistic::getTotalOrders))
                .orElseThrow(() -> new EntityNotFoundException("Самый популярный напиток не найден"));
    }

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void deleteOldStatistics() {
        LocalDateTime fiveYearsAgo = LocalDateTime.now().minusYears(5);
        coffeeOrderStatisticRepository.deleteByFirstOrderDateBefore(fiveYearsAgo);
    }
}
