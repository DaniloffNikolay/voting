package ru.danilov.voting.voting.controllers;

import ru.danilov.voting.voting.models.Person;
import ru.danilov.voting.voting.models.restaurant.Dish;
import ru.danilov.voting.voting.models.restaurant.LunchMenu;
import ru.danilov.voting.voting.models.restaurant.LunchMenuItem;
import ru.danilov.voting.voting.models.restaurant.Restaurant;

import java.time.LocalDate;

public class Util {


    public static Person createTestGuestPerson() {
        Person person = new Person();
        person.setName("Tester");
        person.setRole("guest");

        return person;
    }

    public static Person createTestAdminPerson() {
        Person person = new Person();
        person.setName("Senior tester");
        person.setRole("admin");

        return person;
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
}
