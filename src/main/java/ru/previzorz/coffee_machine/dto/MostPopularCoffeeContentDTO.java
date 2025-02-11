package ru.previzorz.coffee_machine.dto;

public class MostPopularCoffeeContentDTO {

    private String name;
    private Integer totalOrders;

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
}
