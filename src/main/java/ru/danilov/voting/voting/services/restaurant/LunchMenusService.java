package ru.danilov.voting.voting.services.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danilov.voting.voting.models.restaurant.Dish;
import ru.danilov.voting.voting.models.restaurant.LunchMenu;
import ru.danilov.voting.voting.models.restaurant.Restaurant;
import ru.danilov.voting.voting.repositories.restaurant.DishesRepository;
import ru.danilov.voting.voting.repositories.restaurant.LunchMenusRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    public LunchMenu findTodayLunchMenu(Restaurant restaurant) {
        return lunchMenusRepository.findAllByRestaurantAndDateAfter(restaurant, LocalDate.now().minusDays(1)).orElse(null);
    }

    public Optional<LunchMenu> findById(int id) {
        return lunchMenusRepository.findById(id);
    }

    @Transactional
    public LunchMenu save(LunchMenu lunchMenu) {
        return lunchMenusRepository.save(lunchMenu);
    }
}
