package ru.danilov.voting.voting.dto;

import ru.danilov.voting.voting.models.restaurant.Restaurant;

import javax.validation.constraints.NotNull;

/**
 * User: Nikolai Danilov
 * Date: 11.05.2023
 */
public class VoteDTO {

    @NotNull(message = "Restaurant should not be null")
    private Restaurant restaurant;

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
