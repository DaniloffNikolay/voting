package ru.danilov.voting.voting.controllers;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.danilov.voting.voting.VotingApplication;
import ru.danilov.voting.voting.models.Person;
import ru.danilov.voting.voting.models.restaurant.Dish;
import ru.danilov.voting.voting.models.restaurant.LunchMenu;
import ru.danilov.voting.voting.models.restaurant.LunchMenuItem;
import ru.danilov.voting.voting.models.restaurant.Restaurant;
import ru.danilov.voting.voting.services.restaurant.DishesService;
import ru.danilov.voting.voting.services.restaurant.LunchMenuItemsService;
import ru.danilov.voting.voting.services.restaurant.LunchMenusService;
import ru.danilov.voting.voting.services.restaurant.RestaurantsService;
import ru.danilov.voting.voting.services.users.PeopleService;
import ru.danilov.voting.voting.util.BindingResultUtil;
import ru.danilov.voting.voting.util.validators.DishValidator;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(VotingApplication.class);

    private final PeopleService peopleService;
    private final DishesService dishesService;
    private final LunchMenuItemsService lunchMenuItemsService;
    private final LunchMenusService lunchMenusService;
    private final RestaurantsService restaurantsService;

    private final DishValidator dishValidator;

    @Autowired
    public AdminController(PeopleService peopleService, DishesService dishesService, LunchMenuItemsService lunchMenuItemsService,
                           LunchMenusService lunchMenusService, RestaurantsService restaurantsService, DishValidator dishValidator) {
        this.peopleService = peopleService;
        this.dishesService = dishesService;
        this.lunchMenuItemsService = lunchMenuItemsService;
        this.lunchMenusService = lunchMenusService;
        this.restaurantsService = restaurantsService;
        this.dishValidator = dishValidator;
    }

    @PutMapping("/set_role_admin")
    public ResponseEntity<HttpStatus> setRoleAdmin(@RequestBody @Valid Person person,
                                                   BindingResult bindingResult) {
        log.info("POST: /admin/dish");

        if (bindingResult.hasErrors()) {
            BindingResultUtil.getException(bindingResult);
        }

        String role = person.getRole();

        if (role.equals("ROLE_ADMIN")) {
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build();
        }

        person.setRole("ROLE_ADMIN");
        peopleService.save(person);

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PostMapping("/dish")
    public ResponseEntity<Dish> setDish(@RequestBody @Valid Dish dish,
                                        BindingResult bindingResult) {
        log.info("POST: /admin/dish");

        dishValidator.validate(dish, bindingResult);

        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            if (fieldErrors.size() == 1 && bindingResult.hasFieldErrors("name")) {
                log.info("Dish who has this name is already taken, return ");
                return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(dishesService.findFirstByNameIs(dish).orElse(null));
            } else {
                BindingResultUtil.getException(bindingResult);
            }
        }

        Dish responseDish = dishesService.save(dish);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDish);
    }

    @GetMapping("/dish/{id}")
    public ResponseEntity<Dish> getDish(@PathVariable("id") int id) {
        log.info("GET: /admin/dish/" + id);
        Dish dish = dishesService.findById(id).orElse(null);
        return ResponseEntity.status(HttpStatus.OK).body(dish);
    }

    @GetMapping("/dishes")
    public ResponseEntity<List<Dish>> getAllDishes() {
        log.info("GET: /admin/dishes");
        List<Dish> dishes = dishesService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(dishes);
    }

    @PostMapping("/restaurant")
    public ResponseEntity<Restaurant> setRestaurant(@RequestBody Restaurant restaurant,
                                                    BindingResult bindingResult) {
        log.info("POST: /admin/restaurant/");
        if (bindingResult.hasErrors()) {
            BindingResultUtil.getException(bindingResult);
        }

        Restaurant responseRestaurant = restaurantsService.save(restaurant);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseRestaurant);
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<Restaurant> getRestaurant(@PathVariable("id") int id) {
        log.info("GET: /admin/restaurant/" + id);
        Restaurant responseRestaurant = restaurantsService.findById(id).orElse(null);
        return ResponseEntity.status(HttpStatus.OK).body(responseRestaurant);
    }

    @GetMapping("/restaurants")
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        log.info("GET: /admin/restaurants");
        List<Restaurant> restaurants = restaurantsService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(restaurants);
    }

    @PostMapping("/lunch_menu")
    public ResponseEntity<LunchMenu> setLunchMenu(@RequestBody LunchMenu lunchMenu,
                                                  BindingResult bindingResult) {
        log.info("POST: /admin/lunch_menu");
        if (bindingResult.hasErrors()) {
            BindingResultUtil.getException(bindingResult);
        }

        LunchMenu responseLunchMenu = lunchMenusService.save(lunchMenu);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseLunchMenu);
    }

    @GetMapping("/lunch_menu/{id}")
    public ResponseEntity<LunchMenu> getLunchMenu(@PathVariable("id") int id) {
        log.info("GET: /admin/lunch_menu/" + id);
        LunchMenu responseLunchMenu = lunchMenusService.findById(id).orElse(null);
        return ResponseEntity.status(HttpStatus.OK).body(responseLunchMenu);
    }

    @PostMapping("/lunch_menu_item")
    public ResponseEntity<LunchMenuItem> setLunchMenuItem(@RequestBody LunchMenuItem lunchMenuItem,
                                                          BindingResult bindingResult) {
        log.info("POST: /admin/lunch_menu_item");
        if (bindingResult.hasErrors()) {
            BindingResultUtil.getException(bindingResult);
        }

        LunchMenuItem responseLunchMenuItem = lunchMenuItemsService.save(lunchMenuItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseLunchMenuItem);
    }

    @GetMapping("/lunch_menu_item/{id}")
    public ResponseEntity<LunchMenuItem> getLunchMenuItem(@PathVariable("id") int id) {
        log.info("POST: /admin/lunch_menu_item/" + id);
        LunchMenuItem responseLunchMenuItem = lunchMenuItemsService.findById(id).orElse(null);
        return ResponseEntity.status(HttpStatus.OK).body(responseLunchMenuItem);
    }
}