package ru.previzorz.coffee_machine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.previzorz.coffee_machine.entity.CoffeeOrderStatistic;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface CoffeeOrderStatisticRepository extends JpaRepository<CoffeeOrderStatistic, Long> {
    Optional<CoffeeOrderStatistic> findByName(String name);

    void deleteByFirstOrderDateBefore(LocalDateTime dateTime);
}
