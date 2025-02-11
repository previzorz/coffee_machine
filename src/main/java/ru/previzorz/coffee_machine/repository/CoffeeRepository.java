package ru.previzorz.coffee_machine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.previzorz.coffee_machine.entity.Coffee;

import java.util.Optional;

@Repository
public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
    Optional<Coffee> findByName(String name);
}
