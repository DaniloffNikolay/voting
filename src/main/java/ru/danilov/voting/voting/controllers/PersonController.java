package ru.danilov.voting.voting.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.danilov.voting.voting.VotingApplication;
import ru.danilov.voting.voting.models.Person;
import ru.danilov.voting.voting.models.Vote;
import ru.danilov.voting.voting.models.restaurant.LunchMenu;
import ru.danilov.voting.voting.models.restaurant.Restaurant;
import ru.danilov.voting.voting.security.PersonDetails;
import ru.danilov.voting.voting.services.users.PeopleService;
import ru.danilov.voting.voting.services.VotesService;
import ru.danilov.voting.voting.services.restaurant.LunchMenusService;
import ru.danilov.voting.voting.services.restaurant.RestaurantsService;
import ru.danilov.voting.voting.util.VoteUtil;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/people")
public class PersonController {

    private static final Logger log = LoggerFactory.getLogger(VotingApplication.class);

    private final PeopleService peopleService;
    private final VotesService votesService;
    private final RestaurantsService restaurantsService;
    private final LunchMenusService lunchMenusService;

    @Autowired
    public PersonController(PeopleService peopleService, VotesService votesService,
                            RestaurantsService restaurantsService, LunchMenusService lunchMenusService) {
        this.peopleService = peopleService;
        this.votesService = votesService;
        this.restaurantsService = restaurantsService;
        this.lunchMenusService = lunchMenusService;
    }

    @GetMapping
    public ResponseEntity<Person> showUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        log.info("GET: /people");
        Person person = personDetails.getPerson();
        log.info(person.toString());

        return ResponseEntity.status(HttpStatus.OK).body(person);
    }

    @PutMapping("/{personId}/vote")
    public ResponseEntity<HttpStatus> vote(@PathVariable("personId") int personId, @RequestBody Vote vote,
                                           BindingResult bindingResult) {
        log.info("PUT: /people/" + personId + "/vote");
        if (bindingResult.hasErrors()) {
            //TODO
        }
        return VoteUtil.checkVote(peopleService.findById(personId), Optional.of(vote.getRestaurant()), votesService);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable("id") int id) {
        log.info("GET: /people/" + id);

        Person responsePerson = peopleService.findById(id).orElse(null);

        return ResponseEntity.status(HttpStatus.OK).body(responsePerson);
    }

    @PostMapping
    public ResponseEntity<Person> savePerson(@RequestBody Person person,
                                             BindingResult bindingResult) {
        log.info("POST: /people");
        if (bindingResult.hasErrors()) {
            //TODO
        }

        Person responsePerson = peopleService.save(person);

        return ResponseEntity.status(HttpStatus.CREATED).body(responsePerson);
    }

    @DeleteMapping
    public ResponseEntity<Person> deletePerson(@RequestBody Person person,
                                               BindingResult bindingResult) {
        log.info("DELETE: /people");
        if (bindingResult.hasErrors()) {
            //TODO
        }

        peopleService.delete(person);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/restaurants")
    public ResponseEntity<List<Restaurant>> getAllRestaurant() {
        log.info("GET: /people/restaurants");
        return ResponseEntity.status(HttpStatus.OK).body(restaurantsService.findAll());
    }

    @GetMapping("/restaurant")
    public ResponseEntity<Restaurant> getRestaurantByLunchMenu(@RequestBody LunchMenu lunchMenu,
                                                               BindingResult bindingResult) {
        log.info("GET: /people/restaurant");
        if (bindingResult.hasErrors()) {
            //TODO
        }

        return ResponseEntity.status(HttpStatus.OK).body(restaurantsService.findById(lunchMenu.getRestaurant().getId()).orElse(null));
    }

    @GetMapping("/lunch_menu")
    public ResponseEntity<LunchMenu> getAllLunchMenuFromRestaurant(@RequestBody Restaurant restaurant,
                                                                    BindingResult bindingResult) {
        log.info("GET: /people/lunch_menu");
        if (bindingResult.hasErrors()) {
            //TODO
        }

        return ResponseEntity.status(HttpStatus.OK).body(lunchMenusService.findTodayLunchMenu(restaurant));
    }

    @GetMapping("/lunch_menus")
    public ResponseEntity<List<LunchMenu>> getAllLunchMenusToday() {
        log.info("GET: /people/lunch_menus");

        return ResponseEntity.status(HttpStatus.OK).body(lunchMenusService.getAllLunchMenusToday());
    }
}