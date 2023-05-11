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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {

    private final ObjectMapper objectMapper;
    private final MockMvc mockMvc;
    private final Util util;
    private final PeopleService peopleService;
    private final DishesService dishesService;
    private final RestaurantsService restaurantsService;
    private final LunchMenusService lunchMenusService;
    private final LunchMenuItemsService lunchMenuItemsService;
    private final VotesService votesService;

    @Autowired
    public AdminControllerTest(ObjectMapper objectMapper, MockMvc mockMvc, Util util, PeopleService peopleService,
                               DishesService dishesService, RestaurantsService restaurantsService, LunchMenusService lunchMenusService,
                               LunchMenuItemsService lunchMenuItemsService, VotesService votesService) {
        this.objectMapper = objectMapper;
        this.mockMvc = mockMvc;
        this.util = util;
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
    public void postDishThenStatus201Or208AndDishReturned() throws Exception {
        Dish dish = util.createTestDish();

        mockMvc.perform(post("/admin/dish")
                        .content(objectMapper.writeValueAsString(dish))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Rice"));

        mockMvc.perform(post("/admin/dish")
                        .content(objectMapper.writeValueAsString(dish))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAlreadyReported())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Rice"));
    }

    @Test
    public void getDishThenStatus200AndDishConform() throws Exception {
        Dish dish = dishesService.save(util.createTestDish());

        mockMvc.perform(get("/admin/dish/" + dish.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Rice"));
    }

    @Test
    public void getAllDishesThenStatus200AndDishesConform() throws Exception {
        dishesService.save(util.createTestDish());
        dishesService.save(util.createTestDishWithName("Potato"));

        mockMvc.perform(get("/admin/dishes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].name").value("Rice"))
                .andExpect(jsonPath("$.[1].name").value("Potato"));
    }

    @Test
    public void postRestaurantThenStatus201AndRestaurantReturned() throws Exception {
        Restaurant restaurant = util.createTestRestaurant();

        mockMvc.perform(post("/admin/restaurant")
                        .content(objectMapper.writeValueAsString(restaurant))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Star"));
    }

    @Test
    public void getRestaurantThenStatus200AndRestaurantConform() throws Exception {
        Restaurant restaurant = restaurantsService.save(util.createTestRestaurant());

        mockMvc.perform(get("/admin/restaurant/" + restaurant.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Star"));
    }

    @Test
    public void getAllRestaurantsThenStatus200AndRestaurantsConform() throws Exception {
        restaurantsService.save(util.createTestRestaurant());
        restaurantsService.save(util.createTestRestaurantWithName("Moon"));

        mockMvc.perform(get("/admin/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].name").value("Star"))
                .andExpect(jsonPath("$.[1].name").value("Moon"));
    }

    @Test
    public void postLunchMenuThenStatus201AndLunchMenuReturned() throws Exception {
        Restaurant restaurant = restaurantsService.save(util.createTestRestaurant());
        LunchMenu lunchMenu = lunchMenusService.save(util.createTestLunchMenu(restaurant));

        mockMvc.perform(post("/admin/lunch_menu")
                        .content(objectMapper.writeValueAsString(lunchMenu))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()));
    }

    @Test
    public void getLunchMenuThenStatus200AndLunchMenuConform() throws Exception {
        Restaurant restaurant = restaurantsService.save(util.createTestRestaurant());
        LunchMenu lunchMenu = lunchMenusService.save(util.createTestLunchMenu(restaurant));

        mockMvc.perform(get("/admin/lunch_menu/" + lunchMenu.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()));
    }

    @Test
    public void postLunchMenuItemThenStatus201AndLunchMenuItemReturned() throws Exception {
        Restaurant restaurant = restaurantsService.save(util.createTestRestaurant());
        LunchMenu lunchMenu = lunchMenusService.save(util.createTestLunchMenu(restaurant));
        Dish dish = dishesService.save(util.createTestDish());
        LunchMenuItem lunchMenuItem = util.createTestLunchMenuItem(lunchMenu, dish);

        mockMvc.perform(post("/admin/lunch_menu_item")
                        .content(objectMapper.writeValueAsString(lunchMenuItem))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.dish.name").value("Rice"))
                .andExpect(jsonPath("$.price").value(500.50))
                .andExpect(jsonPath("$.lunchMenu.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.lunchMenu.restaurant.name").value("Star"));
    }

    @Test
    public void getLunchMenuItemThenStatus200AndLunchMenuItemConform() throws Exception {
        Restaurant restaurant = restaurantsService.save(util.createTestRestaurant());
        LunchMenu lunchMenu = lunchMenusService.save(util.createTestLunchMenu(restaurant));
        Dish dish = dishesService.save(util.createTestDish());
        LunchMenuItem lunchMenuItem = lunchMenuItemsService.save(util.createTestLunchMenuItem(lunchMenu, dish));

        mockMvc.perform(get("/admin/lunch_menu_item/" + lunchMenuItem.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.dish.name").value("Rice"))
                .andExpect(jsonPath("$.price").value(500.50))
                .andExpect(jsonPath("$.lunchMenu.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.lunchMenu.restaurant.name").value("Star"));
    }
}