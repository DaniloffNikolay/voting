package ru.danilov.voting.voting.services.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danilov.voting.voting.models.restaurant.Dish;
import ru.danilov.voting.voting.repositories.restaurant.DishesRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DishesService {

    private final DishesRepository dishesRepository;

    @Autowired
    public DishesService(DishesRepository dishesRepository) {
        this.dishesRepository = dishesRepository;
    }

    public List<Dish> findAll() {
        return dishesRepository.findAll();
    }
}
