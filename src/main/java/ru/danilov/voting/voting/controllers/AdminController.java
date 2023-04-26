package ru.danilov.voting.voting.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.danilov.voting.voting.VotingApplication;
import ru.danilov.voting.voting.models.restaurant.Dish;
import ru.danilov.voting.voting.models.restaurant.LunchMenu;
import ru.danilov.voting.voting.models.restaurant.LunchMenuItem;
import ru.danilov.voting.voting.models.restaurant.Restaurant;
import ru.danilov.voting.voting.services.PeopleService;
import ru.danilov.voting.voting.services.restaurant.DishesService;
import ru.danilov.voting.voting.services.restaurant.LunchMenuItemsService;
import ru.danilov.voting.voting.services.restaurant.LunchMenusService;
import ru.danilov.voting.voting.services.restaurant.RestaurantsService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(VotingApplication.class);

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

    @PostMapping("/dish")
    public Dish setDish(@RequestBody Dish dish, BindingResult bindingResult) {
        log.info("POST: /admin/dish");
        if (bindingResult.hasErrors()) {
            //TODO
        }

        //TODO check all dishes for a match

        return dishesService.save(dish);
    }

    @GetMapping("/dish/{id}")
    public Dish getDish(@PathVariable("id") int id) {
        log.info("GET: /admin/dish/" + id);
        return dishesService.findById(id).orElse(null);
    }

    @PostMapping("/restaurant")
    public Restaurant setRestaurant(@RequestBody Restaurant restaurant, BindingResult bindingResult) {
        log.info("POST: /admin/restaurant/");
        if (bindingResult.hasErrors()) {
            //TODO
        }

        return restaurantsService.save(restaurant);
    }

    @GetMapping("/restaurant/{id}")
    public Restaurant getRestaurant(@PathVariable("id") int id) {
        log.info("GET: /admin/restaurant/" + id);
        return restaurantsService.findById(id).orElse(null);
    }

    @PostMapping("/restaurant/{id}/lunch_menu")
    public LunchMenu setLunchMenu(@PathVariable("id") int id, @RequestBody LunchMenu lunchMenu, BindingResult bindingResult) {
        log.info("POST: /admin/restaurant/" + id + "/lunch_menu");
        if (bindingResult.hasErrors()) {
            //TODO
        }

        lunchMenu.setRestaurant(restaurantsService.findById(id).orElse(null));

        return lunchMenusService.save(lunchMenu);
    }

    @GetMapping("/restaurant/{restaurantId}/lunch_menu/{lunchMenuId}")
    public LunchMenu getLunchMenu(@PathVariable("restaurantId") int restaurantId, @PathVariable("lunchMenuId") int lunchMenuId) {
        log.info("GET: /admin/restaurant/" + restaurantId + "/lunch_menu/" + lunchMenuId);
        return lunchMenusService.findById(lunchMenuId).orElse(null);
    }

    @PostMapping("/restaurant/{restaurantId}/lunch_menu/{lunchMenuId}/lunch_menu_item")
    public LunchMenuItem setLunchMenuItem(@PathVariable("restaurantId") int restaurantId,
                                          @PathVariable("lunchMenuId") int lunchMenuId,
                                          @RequestBody LunchMenuItem lunchMenuItem,
                                          BindingResult bindingResult) {
        log.info("POST: /admin/restaurant/" + restaurantId + "/lunch_menu/" + lunchMenuId + "/lunch_menu_item");
        if (bindingResult.hasErrors()) {
            //TODO
        }

        return null;
    }

    @GetMapping("/restaurant/{restaurantId}/lunch_menu/{lunchMenuId}/lunch_menu_item/{lunchMenuItemId}")
    public LunchMenuItem getLunchMenuItem(@PathVariable("restaurantId") int restaurantId,
                                  @PathVariable("lunchMenuId") int lunchMenuId,
                                  @PathVariable("lunchMenuItemId") int lunchMenuItemId) {
        log.info("POST: /admin/restaurant/" + restaurantId + "/lunch_menu/" + lunchMenuId + "/lunch_menu_item/" + lunchMenuItemId);
        return lunchMenuItemsService.findById(lunchMenuItemId).orElse(null);
    }
}