package ru.danilov.voting.voting.services.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danilov.voting.voting.models.restaurant.Dish;
import ru.danilov.voting.voting.models.restaurant.LunchMenu;
import ru.danilov.voting.voting.repositories.restaurant.DishesRepository;
import ru.danilov.voting.voting.repositories.restaurant.LunchMenusRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class LunchMenusService {

    private final LunchMenusRepository lunchMenusRepository;

    @Autowired
    public LunchMenusService(LunchMenusRepository lunchMenusRepository) {
        this.lunchMenusRepository = lunchMenusRepository;
    }

    public List<LunchMenu> findAll() {
        return lunchMenusRepository.findAll();
    }
}
