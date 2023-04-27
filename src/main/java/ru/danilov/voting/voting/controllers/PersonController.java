package ru.danilov.voting.voting.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.danilov.voting.voting.VotingApplication;
import ru.danilov.voting.voting.models.Person;
import ru.danilov.voting.voting.models.Vote;
import ru.danilov.voting.voting.models.restaurant.LunchMenuItem;
import ru.danilov.voting.voting.models.restaurant.Restaurant;
import ru.danilov.voting.voting.services.PeopleService;
import ru.danilov.voting.voting.services.VotesService;
import ru.danilov.voting.voting.services.restaurant.DishesService;
import ru.danilov.voting.voting.services.restaurant.LunchMenuItemsService;
import ru.danilov.voting.voting.services.restaurant.LunchMenusService;
import ru.danilov.voting.voting.services.restaurant.RestaurantsService;
import ru.danilov.voting.voting.util.VoteUtil;

import java.util.Optional;

@RestController
@RequestMapping("/people")
public class PersonController {

    private static final Logger log = LoggerFactory.getLogger(VotingApplication.class);

    private final PeopleService peopleService;
    private final VotesService votesService;

    @Autowired
    public PersonController(PeopleService peopleService, VotesService votesService) {
        this.peopleService = peopleService;
        this.votesService = votesService;
    }

    @PutMapping("/{personId}/vote")
    public ResponseEntity<HttpStatus> vote(@PathVariable("personId") int personId, @RequestBody Vote vote, BindingResult bindingResult) {
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
}