package ru.danilov.voting.voting.dto.restaurant;

import javax.validation.constraints.NotEmpty;

/**
 * User: Nikolai Danilov
 * Date: 11.05.2023
 */
public class DishDTO {
    @NotEmpty(message = "Dish name should not be empty")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
