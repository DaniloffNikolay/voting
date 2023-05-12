package ru.danilov.voting.voting;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import ru.danilov.voting.voting.dto.PersonDTO;
import ru.danilov.voting.voting.dto.VoteDTO;
import ru.danilov.voting.voting.models.Person;
import ru.danilov.voting.voting.models.restaurant.Dish;
import ru.danilov.voting.voting.models.restaurant.LunchMenu;
import ru.danilov.voting.voting.models.restaurant.LunchMenuItem;
import ru.danilov.voting.voting.models.restaurant.Restaurant;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class Util {


    public static Person createTestGuestPerson() {
        Person person = new Person();
        person.setName("Tester");
        person.setRole("ROLE_USER");

        return person;
    }

    public static Person createTestAdminPerson() {
        Person person = new Person();
        person.setName("Senior tester");
        person.setRole("ROLE_ADMIN");

        return person;
    }

    public static VoteDTO createTestVoteDTO(Restaurant restaurant) {
        VoteDTO voteDTO = new VoteDTO();
        voteDTO.setRestaurant(restaurant);
        return voteDTO;
    }

    public static Restaurant createTestRestaurant() {
        return createTestRestaurantWithName("Star");
    }

    public static Restaurant createTestRestaurantWithName(String name) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);

        return restaurant;
    }

    public static LunchMenu createTestLunchMenu(Restaurant restaurant) {
        LunchMenu lunchMenu = new LunchMenu();
        lunchMenu.setRestaurant(restaurant);
        lunchMenu.setDate(LocalDate.now());

        return lunchMenu;
    }

    public static LunchMenuItem createTestLunchMenuItem(LunchMenu lunchMenu) {
        LunchMenuItem lunchMenuItem = new LunchMenuItem();
        lunchMenuItem.setLunchMenu(lunchMenu);
        lunchMenuItem.setDish(createTestDish());
        lunchMenuItem.setPrice(500.50);

        return lunchMenuItem;
    }

    public static LunchMenuItem createTestLunchMenuItem(LunchMenu lunchMenu, Dish dish) {
        LunchMenuItem lunchMenuItem = new LunchMenuItem();
        lunchMenuItem.setLunchMenu(lunchMenu);
        lunchMenuItem.setDish(dish);
        lunchMenuItem.setPrice(500.50);

        return lunchMenuItem;
    }

    public static Dish createTestDishWithName(String name) {
        Dish dish = new Dish();
        dish.setName(name);

        return dish;
    }

    public static Dish createTestDish() {
        return createTestDishWithName("Rice");
    }

    public static PersonDTO createTestLoginPersonDTO() {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setName("Tom");
        personDTO.setPassword("password");

        return personDTO;
    }

    public static String saveNewPersonAndGetHimJWTToken(ObjectMapper objectMapper, MockMvc mockMvc) throws Exception {
        PersonDTO personDTO = createTestLoginPersonDTO();
        String jwtToken = mockMvc.perform(post("/auth/registration")
                        .content(objectMapper.writeValueAsString(personDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString()
                .replace("{\"jwt-token\":\"", "")
                .replace("\"}", "");

        return jwtToken;
    }

    public static Person createTestAdmin() {
        Person person = new Person();

        person.setName("admin");
        person.setPassword("$2a$10$oeAbn856Zne9kXbjYHfop.3z3tzvnkU1844R0mZdlmy0DN.4w9NYO");
        person.setRole("ROLE_ADMIN");

        return person;
    }

    public static Person createTestUser() {
        Person person = new Person();

        person.setName("Tester");
        person.setPassword("$2a$10$oeAbn856Zne9kXbjYHfop.3z3tzvnkU1844R0mZdlmy0DN.4w9NYO");
        person.setRole("ROLE_USER");

        return person;
    }

    public static String getAdminJWTToken(ObjectMapper objectMapper, MockMvc mockMvc) throws Exception {
        PersonDTO personDTO = new PersonDTO();

        personDTO.setName("admin");
        personDTO.setPassword("password");

        String jwtToken = mockMvc.perform(post("/auth/login")
                        .content(objectMapper.writeValueAsString(personDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString()
                .replace("{\"jwt-token\":\"", "")
                .replace("\"}", "");

        return jwtToken;
    }
}
