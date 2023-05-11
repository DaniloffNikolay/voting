package ru.danilov.voting.voting.dto.restaurant;

import ru.danilov.voting.voting.models.restaurant.Dish;
import ru.danilov.voting.voting.models.restaurant.LunchMenu;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

/**
 * User: Nikolai Danilov
 * Date: 11.05.2023
 */
public class LunchMenuItemDTO {

    @NotEmpty(message = "Dish should not be empty")
    private DishDTO dish;
    private double price;

    public LunchMenuItemDTO() {
    }

    public LunchMenuItemDTO(DishDTO dish, double price) {
        this.dish = dish;
        this.price = price;
    }

    public DishDTO getDish() {
        return dish;
    }

    public void setDish(DishDTO dish) {
        this.dish = dish;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
