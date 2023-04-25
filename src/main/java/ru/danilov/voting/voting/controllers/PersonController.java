package ru.danilov.voting.voting.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.danilov.voting.voting.services.PeopleService;
import ru.danilov.voting.voting.services.VotesService;
import ru.danilov.voting.voting.services.restaurant.DishesService;
import ru.danilov.voting.voting.services.restaurant.LunchMenuItemsService;
import ru.danilov.voting.voting.services.restaurant.LunchMenusService;
import ru.danilov.voting.voting.services.restaurant.RestaurantsService;
import ru.danilov.voting.voting.util.VoteUtil;

@RestController
@RequestMapping("/people")
public class PersonController {

    private final PeopleService peopleService;
    private final VotesService votesService;
    private final DishesService dishesService;
    private final LunchMenuItemsService lunchMenuItemsService;
    private final LunchMenusService lunchMenusService;
    private final RestaurantsService restaurantsService;

    @Autowired
    public PersonController(PeopleService peopleService, VotesService votesService, DishesService dishesService, LunchMenuItemsService lunchMenuItemsService, LunchMenusService lunchMenusService, RestaurantsService restaurantsService) {
        this.peopleService = peopleService;
        this.votesService = votesService;
        this.dishesService = dishesService;
        this.lunchMenuItemsService = lunchMenuItemsService;
        this.lunchMenusService = lunchMenusService;
        this.restaurantsService = restaurantsService;
    }

    @PutMapping("/{personId}/votes/{restaurantId}")
    public void vote(@PathVariable("personId") int personId, @PathVariable("restaurantId") int restaurantId) {
        VoteUtil.checkVote(peopleService.findById(personId), restaurantsService.findById(restaurantId), votesService);
    }
}
