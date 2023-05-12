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
import ru.danilov.voting.voting.models.Person;
import ru.danilov.voting.voting.models.restaurant.Dish;
import ru.danilov.voting.voting.models.restaurant.LunchMenu;
import ru.danilov.voting.voting.models.restaurant.LunchMenuItem;
import ru.danilov.voting.voting.models.restaurant.Restaurant;
import ru.danilov.voting.voting.services.users.PeopleService;
import ru.danilov.voting.voting.services.VotesService;
import ru.danilov.voting.voting.services.restaurant.DishesService;
import ru.danilov.voting.voting.services.restaurant.LunchMenuItemsService;
import ru.danilov.voting.voting.services.restaurant.LunchMenusService;
import ru.danilov.voting.voting.services.restaurant.RestaurantsService;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {

    private final ObjectMapper objectMapper;
    private final MockMvc mockMvc;
    private final PeopleService peopleService;
    private final DishesService dishesService;
    private final RestaurantsService restaurantsService;
    private final LunchMenusService lunchMenusService;
    private final LunchMenuItemsService lunchMenuItemsService;
    private final VotesService votesService;

    @Autowired
    public AdminControllerTest(ObjectMapper objectMapper, MockMvc mockMvc, PeopleService peopleService,
                               DishesService dishesService, RestaurantsService restaurantsService, LunchMenusService lunchMenusService,
                               LunchMenuItemsService lunchMenuItemsService, VotesService votesService) {
        this.objectMapper = objectMapper;
        this.mockMvc = mockMvc;
        this.peopleService = peopleService;
        this.dishesService = dishesService;
        this.restaurantsService = restaurantsService;
        this.lunchMenusService = lunchMenusService;
        this.lunchMenuItemsService = lunchMenuItemsService;
        this.votesService = votesService;
    }

    @AfterEach
    public void resetDb() {
        lunchMenuItemsService.deleteAll();
        dishesService.deleteAll();
        lunchMenusService.deleteAll();
        votesService.deleteAll();
        peopleService.deleteAll();
        restaurantsService.deleteAll();
    }

    @Test
    public void savePersonThenGetHimAndStatus200() throws Exception {
        peopleService.save(Util.createTestAdmin());
        Person person = peopleService.save(Util.createTestUser());

        String jwtToken = Util.getAdminJWTToken(objectMapper, mockMvc);

        mockMvc.perform(get("/admin/person/" + person.getId())
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Tester"))
                .andExpect(jsonPath("$.role").value("ROLE_USER"));
    }

    @Test
    public void saveTwoPersonThenGetAllPeopleThenStatus200AndPeopleReturned() throws Exception {
        peopleService.save(Util.createTestAdmin());
        peopleService.save(Util.createTestUser());

        String jwtToken = Util.getAdminJWTToken(objectMapper, mockMvc);

        mockMvc.perform(get("/admin/people")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id").isNumber())
                .andExpect(jsonPath("$.[0].name").value("admin"))
                .andExpect(jsonPath("$.[0].role").value("ROLE_ADMIN"))
                .andExpect(jsonPath("$.[1].id").isNumber())
                .andExpect(jsonPath("$.[1].name").value("Tester"))
                .andExpect(jsonPath("$.[1].role").value("ROLE_USER"));
    }


    @Test
    public void setRoleAdminThenStatus202ThenAgainSetRoleAdminThenStatus208() throws Exception {
        peopleService.save(Util.createTestAdmin());
        Person person = peopleService.save(Util.createTestUser());

        String jwtToken = Util.getAdminJWTToken(objectMapper, mockMvc);

        mockMvc.perform(put("/admin/set_role_admin/" + person.getId())
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isAccepted());

        mockMvc.perform(put("/admin/set_role_admin/" + person.getId())
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isAlreadyReported());

    }

    @Test
    public void postDishThenStatus201Or208AndDishReturned() throws Exception {
        peopleService.save(Util.createTestAdmin());
        String jwtToken = Util.getAdminJWTToken(objectMapper, mockMvc);

        Dish dish = Util.createTestDish();

        mockMvc.perform(post("/admin/dish")
                        .header("Authorization", "Bearer " + jwtToken)
                        .content(objectMapper.writeValueAsString(dish))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Rice"));

        mockMvc.perform(post("/admin/dish")
                        .header("Authorization", "Bearer " + jwtToken)
                        .content(objectMapper.writeValueAsString(dish))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAlreadyReported())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Rice"));
    }

    @Test
    public void getDishThenStatus200AndDishConform() throws Exception {
        peopleService.save(Util.createTestAdmin());
        String jwtToken = Util.getAdminJWTToken(objectMapper, mockMvc);

        Dish dish = dishesService.save(Util.createTestDish());

        mockMvc.perform(get("/admin/dish/" + dish.getId())
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Rice"));
    }

    @Test
    public void getAllDishesThenStatus200AndDishesConform() throws Exception {
        peopleService.save(Util.createTestAdmin());
        String jwtToken = Util.getAdminJWTToken(objectMapper, mockMvc);

        dishesService.save(Util.createTestDish());
        dishesService.save(Util.createTestDishWithName("Potato"));

        mockMvc.perform(get("/admin/dishes")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].name").value("Rice"))
                .andExpect(jsonPath("$.[1].name").value("Potato"));
    }

    @Test
    public void postRestaurantThenStatus201AndRestaurantReturned() throws Exception {
        peopleService.save(Util.createTestAdmin());
        String jwtToken = Util.getAdminJWTToken(objectMapper, mockMvc);

        Restaurant restaurant = Util.createTestRestaurant();

        mockMvc.perform(post("/admin/restaurant")
                        .header("Authorization", "Bearer " + jwtToken)
                        .content(objectMapper.writeValueAsString(restaurant))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Star"));
    }

    @Test
    public void getRestaurantThenStatus200AndRestaurantConform() throws Exception {
        peopleService.save(Util.createTestAdmin());
        String jwtToken = Util.getAdminJWTToken(objectMapper, mockMvc);

        Restaurant restaurant = restaurantsService.save(Util.createTestRestaurant());

        mockMvc.perform(get("/admin/restaurant/" + restaurant.getId())
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Star"));
    }

    @Test
    public void getAllRestaurantsThenStatus200AndRestaurantsConform() throws Exception {
        peopleService.save(Util.createTestAdmin());
        String jwtToken = Util.getAdminJWTToken(objectMapper, mockMvc);

        restaurantsService.save(Util.createTestRestaurant());
        restaurantsService.save(Util.createTestRestaurantWithName("Moon"));

        mockMvc.perform(get("/admin/restaurants")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].name").value("Star"))
                .andExpect(jsonPath("$.[1].name").value("Moon"));
    }

    @Test
    public void postLunchMenuThenStatus201AndLunchMenuReturned() throws Exception {
        peopleService.save(Util.createTestAdmin());
        String jwtToken = Util.getAdminJWTToken(objectMapper, mockMvc);

        Restaurant restaurant = restaurantsService.save(Util.createTestRestaurant());
        LunchMenu lunchMenu = lunchMenusService.save(Util.createTestLunchMenu(restaurant));

        mockMvc.perform(post("/admin/lunch_menu")
                        .header("Authorization", "Bearer " + jwtToken)
                        .content(objectMapper.writeValueAsString(lunchMenu))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()));
    }

    @Test
    public void getLunchMenuThenStatus200AndLunchMenuConform() throws Exception {
        peopleService.save(Util.createTestAdmin());
        String jwtToken = Util.getAdminJWTToken(objectMapper, mockMvc);

        Restaurant restaurant = restaurantsService.save(Util.createTestRestaurant());
        LunchMenu lunchMenu = lunchMenusService.save(Util.createTestLunchMenu(restaurant));

        mockMvc.perform(get("/admin/lunch_menu/" + lunchMenu.getId())
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()));
    }

    @Test
    public void postLunchMenuItemThenStatus201AndLunchMenuItemReturned() throws Exception {
        peopleService.save(Util.createTestAdmin());
        String jwtToken = Util.getAdminJWTToken(objectMapper, mockMvc);

        Restaurant restaurant = restaurantsService.save(Util.createTestRestaurant());
        LunchMenu lunchMenu = lunchMenusService.save(Util.createTestLunchMenu(restaurant));
        Dish dish = dishesService.save(Util.createTestDish());
        LunchMenuItem lunchMenuItem = Util.createTestLunchMenuItem(lunchMenu, dish);

        mockMvc.perform(post("/admin/lunch_menu_item/" + lunchMenu.getId())
                        .header("Authorization", "Bearer " + jwtToken)
                        .content(objectMapper.writeValueAsString(lunchMenuItem))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.dish.name").value("Rice"))
                .andExpect(jsonPath("$.price").value(500.50));
    }

    @Test
    public void getLunchMenuItemThenStatus200AndLunchMenuItemConform() throws Exception {
        peopleService.save(Util.createTestAdmin());
        String jwtToken = Util.getAdminJWTToken(objectMapper, mockMvc);

        Restaurant restaurant = restaurantsService.save(Util.createTestRestaurant());
        LunchMenu lunchMenu = lunchMenusService.save(Util.createTestLunchMenu(restaurant));
        Dish dish = dishesService.save(Util.createTestDish());
        LunchMenuItem lunchMenuItem = lunchMenuItemsService.save(Util.createTestLunchMenuItem(lunchMenu, dish));

        mockMvc.perform(get("/admin/lunch_menu_item/" + lunchMenuItem.getId())
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.dish.name").value("Rice"))
                .andExpect(jsonPath("$.price").value(500.50));
    }
}