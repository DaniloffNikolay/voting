package ru.danilov.voting.voting.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.danilov.voting.voting.models.Person;
import ru.danilov.voting.voting.models.Vote;
import ru.danilov.voting.voting.models.restaurant.Dish;
import ru.danilov.voting.voting.models.restaurant.LunchMenu;
import ru.danilov.voting.voting.models.restaurant.LunchMenuItem;
import ru.danilov.voting.voting.models.restaurant.Restaurant;
import ru.danilov.voting.voting.services.PeopleService;
import ru.danilov.voting.voting.services.VotesService;
import ru.danilov.voting.voting.services.restaurant.DishesService;
import ru.danilov.voting.voting.services.restaurant.LunchMenuItemsService;
import ru.danilov.voting.voting.services.restaurant.LunchMenusService;
import ru.danilov.voting.voting.services.restaurant.RestaurantsService;
import ru.danilov.voting.voting.util.VoteUtil;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/main")
public class MainController {

    private final PeopleService peopleService;
    private final VotesService votesService;
    private final DishesService dishesService;
    private final LunchMenuItemsService lunchMenuItemsService;
    private final LunchMenusService lunchMenusService;
    private final RestaurantsService restaurantsService;

    @Autowired
    public MainController(PeopleService peopleService, VotesService votesService, DishesService dishesService, LunchMenuItemsService lunchMenuItemsService, LunchMenusService lunchMenusService, RestaurantsService restaurantsService) {
        this.peopleService = peopleService;
        this.votesService = votesService;
        this.dishesService = dishesService;
        this.lunchMenuItemsService = lunchMenuItemsService;
        this.lunchMenusService = lunchMenusService;
        this.restaurantsService = restaurantsService;
    }

    @PutMapping("/people/{personId}/votes/{restaurantId}")
    public void vote(@PathVariable("personId") int personId, @PathVariable("restaurantId") int restaurantId) {
        Vote vote = VoteUtil.setVote(peopleService.findById(personId), restaurantsService.findById(restaurantId));
        if (vote != null) {
            Optional<Vote> checkVote = votesService.findAllTodayVotesWhereId(vote.getPerson());

            if (checkVote.isPresent()) {
                if (VoteUtil.checkTimeVote(vote, checkVote.get())) {
                    System.out.println("голос уже есть и новый после 11:00 (ничего не создаем)");
                } else {
                    System.out.println("голос уже есть и новый до 11:00 (меняем старый голос)");
                }
            } else {
                System.out.println("голоса нет и создаем новый");
            }
        }
    }

    @GetMapping("/people")
    public List<Person> getFirst() {
        return peopleService.findAll();
    }

    @GetMapping("/votes")
    public List<Vote> getAllVotes() {
        return votesService.findAll();
    }

    @GetMapping("/dishes")
    public List<Dish> getAllDishes() {
        return dishesService.findAll();
    }

    @GetMapping("/lunchMenuItems")
    public List<LunchMenuItem> getAllLunchMenuItems() {
        return lunchMenuItemsService.findAll();
    }

    @GetMapping("/lunchMenus")
    public List<LunchMenu> getAllLunchMenus() {
        return lunchMenusService.findAll();
    }

    @GetMapping("/restaurants")
    public List<Restaurant> getAllRestaurants() {
        return restaurantsService.findAll();
    }
}
