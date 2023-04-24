package ru.danilov.voting.voting.services.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danilov.voting.voting.models.Person;
import ru.danilov.voting.voting.models.restaurant.Restaurant;
import ru.danilov.voting.voting.repositories.restaurant.RestaurantsRepository;

import java.util.List;
import java.util.Optional;

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

    public Optional<Restaurant> findById(int id) {
        return restaurantsRepository.findById(id);
    }
}
