package ru.danilov.voting.voting.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.danilov.voting.voting.Util;
import ru.danilov.voting.voting.dto.PersonDTO;
import ru.danilov.voting.voting.dto.PersonOnlyWithNameDTO;
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

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {
    private final ObjectMapper objectMapper;
    private final MockMvc mockMvc;
    private final PeopleService peopleService;
    private final RestaurantsService restaurantsService;
    private final LunchMenusService lunchMenusService;
    private final VotesService votesService;


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
    public void getPersonWithJWTToken() throws Exception {
        String jwtToken = Util.saveNewPersonAndGetHimJWTToken(objectMapper, mockMvc);

        mockMvc.perform(get("/people")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Tom"));
    }


    @Test
    public void putVoteThenFirstStatus201ThenPutVoteAgainThenStatus208Or202() throws Exception {
        Vote vote = createTestVote(peopleService.save(Util.createTestGuestPerson()));

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
        Vote vote = createTestVote(peopleService.save(Util.createTestAdminPerson()));

        mockMvc.perform(put("/people/" + vote.getPerson().getId() + "/vote")
                        .content(objectMapper.writeValueAsString(vote))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void givenPersonWhenAddThenStatus201AndPersonReturned() throws Exception {
        PersonOnlyWithNameDTO personOnlyWithNameDTO = new PersonOnlyWithNameDTO();
        personOnlyWithNameDTO.setName("Som");
        String jwtToken = Util.saveNewPersonAndGetHimJWTToken(objectMapper, mockMvc);

        mockMvc.perform(post("/people")
                        .header("Authorization", "Bearer " + jwtToken)
                        .content(objectMapper.writeValueAsString(personOnlyWithNameDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Som"));
    }

    @Test
    public void deletePersonThenStatus204() throws Exception {
        String jwtToken = Util.saveNewPersonAndGetHimJWTToken(objectMapper, mockMvc);

        mockMvc.perform(delete("/people")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNoContent());
    }

    @Test
    public void getAllRestaurantsThenStatus200AndRestaurantConform() throws Exception {
        restaurantsService.save(Util.createTestRestaurant());
        String jwtToken = Util.saveNewPersonAndGetHimJWTToken(objectMapper, mockMvc);

        mockMvc.perform(get("/people/restaurants")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].name").value("Star"));
    }

    @Test
    public void getLunchMenusTodayThenStatus200AndLunchMenusConform() throws Exception {
        Restaurant restaurantOne = restaurantsService.save(Util.createTestRestaurant());
        Restaurant restaurantTwo = restaurantsService.save(Util.createTestRestaurantWithName("Moon"));
        lunchMenusService.save(Util.createTestLunchMenu(restaurantOne));
        lunchMenusService.save(Util.createTestLunchMenu(restaurantTwo));

        String jwtToken = Util.saveNewPersonAndGetHimJWTToken(objectMapper, mockMvc);

        mockMvc.perform(get("/people/lunch_menus")
                .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    private Vote createTestVote(Person person) {
        Restaurant restaurant = Util.createTestRestaurant();
        Restaurant restaurantWithId = restaurantsService.save(restaurant);
        Vote vote = new Vote();
        vote.setPerson(person);
        vote.setRestaurant(restaurantWithId);

        return vote;
    }
}
