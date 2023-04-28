package ru.danilov.voting.voting.services.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danilov.voting.voting.models.restaurant.Dish;
import ru.danilov.voting.voting.models.restaurant.LunchMenu;
import ru.danilov.voting.voting.repositories.restaurant.DishesRepository;

import java.util.List;
import java.util.Optional;

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

    public Optional<Dish> findFirstByNameIs(Dish dish) {
        return dishesRepository.findFirstByNameIs(dish.getName());
    }

    public Optional<Dish> findById(int id) {
        return dishesRepository.findById(id);
    }

    @Transactional
    public Dish save(Dish dish) {
        return dishesRepository.save(dish);
    }

    @Transactional
    public void deleteAll() {
        dishesRepository.deleteAll();
    }
}
