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
import ru.danilov.voting.voting.models.restaurant.Restaurant;
import ru.danilov.voting.voting.services.PeopleService;
import ru.danilov.voting.voting.services.VotesService;
import ru.danilov.voting.voting.services.restaurant.RestaurantsService;

import java.time.LocalTime;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {
    private ObjectMapper objectMapper;
    private PeopleService peopleService;
    private RestaurantsService restaurantsService;
    private VotesService votesService;
    private MockMvc mockMvc;

    @Autowired
    public PersonControllerTest(ObjectMapper objectMapper, PeopleService peopleService,
                                RestaurantsService restaurantsService, VotesService votesService, MockMvc mockMvc) {
        this.objectMapper = objectMapper;
        this.peopleService = peopleService;
        this.restaurantsService = restaurantsService;
        this.votesService = votesService;
        this.mockMvc = mockMvc;
    }

    @AfterEach
    public void resetDb() {
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
    public void getPersonThenStatus200andPersonConform() throws Exception {
        Person person = peopleService.save(createTestGuestPerson());


        mockMvc.perform(get("/people/" + person.getId())
                        .content(objectMapper.writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Tester"))
                .andExpect(jsonPath("$.role").value("guest"));
    }

    @Test
    public void givenPersonWhenAddThenStatus201andPersonReturned() throws Exception {
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
    public void getAllRestaurantsThenStatus200andRestaurantConform() throws Exception {
        restaurantsService.save(createTestRestaurant());

        mockMvc.perform(get("/people/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].name").value("Star"));
    }

    @Test
    public void getAllLunchMenusFromRestaurantThenStatus200andRestaurantConform() throws Exception {
        restaurantsService.save(createTestRestaurant());

        mockMvc.perform(get("/people/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].name").value("Star"));
    }

    private Vote createTestVote(Person person) {
        Restaurant restaurant = createTestRestaurant();
        Restaurant restaurantWithId = restaurantsService.save(restaurant);
        Vote vote = new Vote();
        vote.setPerson(person);
        vote.setRestaurant(restaurantWithId);

        return vote;
    }

    private Person createTestGuestPerson() {
        Person person = new Person();
        person.setName("Tester");
        person.setRole("guest");
        return person;
    }

    private Person createTestAdminPerson() {
        Person person = new Person();
        person.setName("Senior tester");
        person.setRole("admin");
        return person;
    }

    private Restaurant createTestRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Star");
        return restaurant;
    }
}
