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
import ru.danilov.voting.voting.dto.PersonDTO;
import ru.danilov.voting.voting.dto.PersonOnlyWithNameDTO;
import ru.danilov.voting.voting.dto.VoteDTO;
import ru.danilov.voting.voting.dto.restaurant.LunchMenuDTO;
import ru.danilov.voting.voting.dto.restaurant.RestaurantDTO;
import ru.danilov.voting.voting.models.Person;
import ru.danilov.voting.voting.models.Vote;
import ru.danilov.voting.voting.security.PersonDetails;
import ru.danilov.voting.voting.services.users.PeopleService;
import ru.danilov.voting.voting.services.VotesService;
import ru.danilov.voting.voting.services.restaurant.LunchMenusService;
import ru.danilov.voting.voting.services.restaurant.RestaurantsService;
import ru.danilov.voting.voting.util.BindingResultUtil;
import ru.danilov.voting.voting.util.ErrorResponse;
import ru.danilov.voting.voting.util.ModelMapperUtil;
import ru.danilov.voting.voting.util.exceptions.PersonNotFoundException;
import ru.danilov.voting.voting.util.VoteUtil;
import ru.danilov.voting.voting.util.exceptions.ValidationFailedException;

import javax.validation.Valid;
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
    private final ModelMapperUtil modelMapperUtil;

    @Autowired
    public PersonController(PeopleService peopleService, VotesService votesService,
                            RestaurantsService restaurantsService, LunchMenusService lunchMenusService, ModelMapperUtil modelMapperUtil) {
        this.peopleService = peopleService;
        this.votesService = votesService;
        this.restaurantsService = restaurantsService;
        this.lunchMenusService = lunchMenusService;
        this.modelMapperUtil = modelMapperUtil;
    }

    @GetMapping
    public ResponseEntity<PersonDTO> showUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        log.info("GET: /people");
        Person person = personDetails.getPerson();
        person.setPassword(null);

        return ResponseEntity.status(HttpStatus.OK).body(modelMapperUtil.convertToPersonDTO(person));
    }

    @PostMapping("/vote")
    public ResponseEntity<HttpStatus> vote(@RequestBody @Valid VoteDTO voteDTO,
                                           BindingResult bindingResult) {
        log.info("PUT: /people/vote");

        if (bindingResult.hasErrors()) {
            BindingResultUtil.getException(bindingResult);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        Vote vote = modelMapperUtil.convertToVote(voteDTO);
        vote.setPerson(personDetails.getPerson());

        return VoteUtil.checkVote(peopleService.findById(personDetails.getPerson().getId()), Optional.of(vote.getRestaurant()), votesService);
    }

    @PostMapping
    public ResponseEntity<PersonOnlyWithNameDTO> savePerson(@RequestBody PersonOnlyWithNameDTO personOnlyWithNameDTO,
                                             BindingResult bindingResult) {
        log.info("POST: /people");
        if (bindingResult.hasErrors()) {
            BindingResultUtil.getException(bindingResult);
        }

        Person person = modelMapperUtil.convertToPerson(personOnlyWithNameDTO);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        person.setPassword(personDetails.getPassword());

        Person responsePerson = peopleService.save(person);

        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapperUtil.convertToPersonOnlyWithNameDTO(responsePerson));
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deletePerson() {
        log.info("DELETE: /people");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        peopleService.delete(personDetails.getPerson());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/restaurants")
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurant() {
        log.info("GET: /people/restaurants");

        return ResponseEntity.status(HttpStatus.OK).body(modelMapperUtil.convertToListRestaurantDTO(restaurantsService.findAll()));
    }

    @GetMapping("/lunch_menus")
    public ResponseEntity<List<LunchMenuDTO>> getAllLunchMenusToday() {
        log.info("GET: /people/lunch_menus");

        return ResponseEntity.status(HttpStatus.OK).body(modelMapperUtil.convertToListLunchMenuDTO(lunchMenusService.getAllLunchMenusToday()));
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(PersonNotFoundException e) {
        ErrorResponse response = new ErrorResponse("Person with this id wasn't found!");

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(ValidationFailedException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}