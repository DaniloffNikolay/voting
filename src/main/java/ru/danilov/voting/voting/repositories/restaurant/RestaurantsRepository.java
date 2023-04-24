package ru.danilov.voting.voting.repositories.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.danilov.voting.voting.models.restaurant.Restaurant;

public interface RestaurantsRepository extends JpaRepository<Restaurant, Integer> {
}
