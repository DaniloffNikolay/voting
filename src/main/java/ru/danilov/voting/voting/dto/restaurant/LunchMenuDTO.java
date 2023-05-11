package ru.danilov.voting.voting.dto.restaurant;

import ru.danilov.voting.voting.models.restaurant.LunchMenuItem;
import ru.danilov.voting.voting.models.restaurant.Restaurant;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * User: Nikolai Danilov
 * Date: 11.05.2023
 */
public class LunchMenuDTO {

    @NotEmpty(message = "Restaurant should not be empty")
    private RestaurantDTO restaurant;
    private List<LunchMenuItemDTO> lunchMenuItems;

    public LunchMenuDTO() {
    }

    public LunchMenuDTO(RestaurantDTO restaurant, List<LunchMenuItemDTO> lunchMenuItems) {
        this.restaurant = restaurant;
        this.lunchMenuItems = lunchMenuItems;
    }

    public RestaurantDTO getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantDTO restaurant) {
        this.restaurant = restaurant;
    }

    public List<LunchMenuItemDTO> getLunchMenuItems() {
        return lunchMenuItems;
    }

    public void setLunchMenuItems(List<LunchMenuItemDTO> lunchMenuItems) {
        this.lunchMenuItems = lunchMenuItems;
    }
}
