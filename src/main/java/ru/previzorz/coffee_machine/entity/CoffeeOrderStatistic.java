package ru.previzorz.coffee_machine.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

import java.time.LocalDateTime;

@Entity
@Table(name = "coffee_order_statistics")
public class CoffeeOrderStatistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Min(0)
    @Column(nullable = false)
    private Integer totalOrders;

    @Column(nullable = false, updatable = false)
    private LocalDateTime firstOrderDate;

    @PrePersist
    @PreUpdate
    private void normalizeName() {
        this.name = this.name.toLowerCase();
    }

    public CoffeeOrderStatistic() {

    }

    public CoffeeOrderStatistic(String name, Integer totalOrders, LocalDateTime firstOrderDate) {
        this.name = name;
        this.totalOrders = totalOrders;
        this.firstOrderDate = firstOrderDate;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(Integer totalOrders) {
        this.totalOrders = totalOrders;
    }

    public LocalDateTime getFirstOrderDate() {
        return firstOrderDate;
    }

    public void setFirstOrderDate(LocalDateTime firstOrderDate) {
        this.firstOrderDate = firstOrderDate;
    }
}
