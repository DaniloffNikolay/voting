package ru.danilov.voting.voting.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.danilov.voting.voting.models.restaurant.LunchMenu;
import ru.danilov.voting.voting.models.restaurant.Restaurant;
import ru.danilov.voting.voting.services.PeopleService;
import ru.danilov.voting.voting.services.restaurant.DishesService;
import ru.danilov.voting.voting.services.restaurant.LunchMenuItemsService;
import ru.danilov.voting.voting.services.restaurant.LunchMenusService;
import ru.danilov.voting.voting.services.restaurant.RestaurantsService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final PeopleService peopleService;
    private final DishesService dishesService;
    private final LunchMenuItemsService lunchMenuItemsService;
    private final LunchMenusService lunchMenusService;
    private final RestaurantsService restaurantsService;

    @Autowired
    public AdminController(PeopleService peopleService, DishesService dishesService, LunchMenuItemsService lunchMenuItemsService, LunchMenusService lunchMenusService, RestaurantsService restaurantsService) {
        this.peopleService = peopleService;
        this.dishesService = dishesService;
        this.lunchMenuItemsService = lunchMenuItemsService;
        this.lunchMenusService = lunchMenusService;
        this.restaurantsService = restaurantsService;
    }

    @PostMapping("/restaurant")
    public Restaurant setRestaurant(@RequestBody Restaurant restaurant, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

        }

        return restaurantsService.save(restaurant);
    }

    @GetMapping("/restaurant/{id}")
    public Restaurant getRestaurant(@PathVariable("id") int id) {
        return restaurantsService.findById(id).orElse(null);
    }

    @PostMapping("/restaurant/{id}/lunch_menu")
    public LunchMenu setLunchMenu(@PathVariable("id") int id, @RequestBody LunchMenu lunchMenu, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {

        }

        lunchMenu.setRestaurant(restaurantsService.findById(id).orElse(null));

        return lunchMenusService.save(lunchMenu);
    }

    @GetMapping("/restaurant/{restaurantId}/lunch_menu/{lunchMenuId}")
    public LunchMenu getLunchMenu(@PathVariable("restaurantId") int restaurantId, @PathVariable("lunchMenuId") int lunchMenuId) {
        return lunchMenusService.findById(lunchMenuId).orElse(null);
    }


}