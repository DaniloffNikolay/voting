package ru.danilov.voting.voting.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.danilov.voting.voting.models.Person;
import ru.danilov.voting.voting.models.Vote;
import ru.danilov.voting.voting.models.restaurant.LunchMenu;
import ru.danilov.voting.voting.models.restaurant.Restaurant;
import ru.danilov.voting.voting.services.users.PeopleService;
import ru.danilov.voting.voting.services.VotesService;
import ru.danilov.voting.voting.services.restaurant.LunchMenusService;
import ru.danilov.voting.voting.services.restaurant.RestaurantsService;

import java.time.LocalTime;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.danilov.voting.voting.controllers.Util.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {
    private final ObjectMapper objectMapper;
    private final PeopleService peopleService;
    private final RestaurantsService restaurantsService;
    private final LunchMenusService lunchMenusService;
    private final VotesService votesService;
    private final MockMvc mockMvc;

    @Autowired
    public PersonControllerTest(ObjectMapper objectMapper, PeopleService peopleService,
                                RestaurantsService restaurantsService, LunchMenusService lunchMenusService, VotesService votesService, MockMvc mockMvc) {
        this.objectMapper = objectMapper;
        this.peopleService = peopleService;
        this.restaurantsService = restaurantsService;
        this.lunchMenusService = lunchMenusService;
        this.votesService = votesService;
        this.mockMvc = mockMvc;
    }

    @AfterEach
    public void resetDb() {
        lunchMenusService.deleteAll();
        votesService.deleteAll();
        peopleService.deleteAll();
        restaurantsService.deleteAll();
    }

    @Test
    public void putVoteThenFirstStatus201ThenPutVoteAgainThenStatus208Or202() throws Exception {
        Vote vote = createTestVote(peopleService.save(createTestGuestPerson()));

        mockMvc.perform(put("/people/" + vote.getPerson().getId() + "/vote")
                        .content(objectMapper.writeValueAsString(vote))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        Restaurant restaurant = new Restaurant();
        restaurant.setName("Moon");
        Restaurant restaurantWithId = restaurantsService.save(restaurant);

        vote.setId(0);
        vote.setRestaurant(restaurantWithId);

        if (LocalTime.now().isAfter(LocalTime.of(11, 0))) {
            mockMvc.perform(put("/people/" + vote.getPerson().getId() + "/vote")
                            .content(objectMapper.writeValueAsString(vote))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isAlreadyReported());
        } else {
            mockMvc.perform(put("/people/" + vote.getPerson().getId() + "/vote")
                            .content(objectMapper.writeValueAsString(vote))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isAccepted());
        }
    }

    @Test
    public void putVoteWhenPersonHasRoleAdminThenStatus403() throws Exception {
        Vote vote = createTestVote(peopleService.save(createTestAdminPerson()));

        mockMvc.perform(put("/people/" + vote.getPerson().getId() + "/vote")
                        .content(objectMapper.writeValueAsString(vote))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getPersonThenStatus200AndPersonConform() throws Exception {
        Person person = peopleService.save(createTestGuestPerson());


        mockMvc.perform(get("/people/" + person.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Tester"))
                .andExpect(jsonPath("$.role").value("guest"));
    }

    @Test
    public void givenPersonWhenAddThenStatus201AndPersonReturned() throws Exception {
        Person person = createTestGuestPerson();

        mockMvc.perform(post("/people")
                        .content(objectMapper.writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Tester"))
                .andExpect(jsonPath("$.role").value("guest"));
    }

    @Test
    public void deletePersonThenStatus204() throws Exception {
        Person person = createTestGuestPerson();
        peopleService.save(person);

        mockMvc.perform(delete("/people")
                        .content(objectMapper.writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void getAllRestaurantsThenStatus200AndRestaurantConform() throws Exception {
        restaurantsService.save(createTestRestaurant());

        mockMvc.perform(get("/people/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].name").value("Star"));
    }

    @Test
    public void getRestaurantByLunchMenuThenStatus200AndRestaurantConform() throws Exception {
        Restaurant restaurant = restaurantsService.save(createTestRestaurant());
        LunchMenu lunchMenu = lunchMenusService.save(createTestLunchMenu(restaurant));


        mockMvc.perform(get("/people/restaurant")
                        .content(objectMapper.writeValueAsString(lunchMenu))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Star"));
    }


    @Test
    public void getLunchMenuFromRestaurantThenStatus200AndLunchMenuConform() throws Exception {
        Restaurant restaurant = restaurantsService.save(createTestRestaurant());
        lunchMenusService.save(createTestLunchMenu(restaurant));

        mockMvc.perform(get("/people/lunch_menu")
                        .content(objectMapper.writeValueAsString(restaurant))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.restaurant.id").isNumber())
                .andExpect(jsonPath("$.restaurant.name").value("Star"));
    }

    @Test
    public void getLunchMenusTodayThenStatus200AndLunchMenusConform() throws Exception {
        Restaurant restaurantOne = restaurantsService.save(createTestRestaurant());
        Restaurant restaurantTwo = restaurantsService.save(createTestRestaurantWithName("Moon"));
        lunchMenusService.save(createTestLunchMenu(restaurantOne));
        lunchMenusService.save(createTestLunchMenu(restaurantTwo));

        mockMvc.perform(get("/people/lunch_menus"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id").isNumber())
                .andExpect(jsonPath("$.[1].id").isNumber());
    }

    private Vote createTestVote(Person person) {
        Restaurant restaurant = createTestRestaurant();
        Restaurant restaurantWithId = restaurantsService.save(restaurant);
        Vote vote = new Vote();
        vote.setPerson(person);
        vote.setRestaurant(restaurantWithId);

        return vote;
    }
}
