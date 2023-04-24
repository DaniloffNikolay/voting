package ru.danilov.voting.voting.services.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danilov.voting.voting.models.Person;
import ru.danilov.voting.voting.models.restaurant.Restaurant;
import ru.danilov.voting.voting.repositories.restaurant.RestaurantsRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RestaurantsService {

    private final RestaurantsRepository restaurantsRepository;

    @Autowired
    public RestaurantsService(RestaurantsRepository restaurantsRepository) {
        this.restaurantsRepository = restaurantsRepository;
    }

    public List<Restaurant> findAll() {
        return restaurantsRepository.findAll();
    }
}
