package ru.danilov.voting.voting.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.danilov.voting.voting.models.restaurant.Dish;
import ru.danilov.voting.voting.services.restaurant.DishesService;

import java.util.Optional;

@Component
public class DishValidator implements Validator {

    private final DishesService dishesService;

    @Autowired
    public DishValidator(DishesService dishesService) {
        this.dishesService = dishesService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Dish.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Dish dish = (Dish) target;

        Optional<Dish> returnedDish = dishesService.findFirstByNameIs(dish);

        if (returnedDish.isPresent()) {
            errors.rejectValue("name", "", "This dish is already taken");
        }
    }
}
