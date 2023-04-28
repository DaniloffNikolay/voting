package ru.danilov.voting.voting.repositories.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.danilov.voting.voting.models.restaurant.LunchMenu;
import ru.danilov.voting.voting.models.restaurant.Restaurant;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LunchMenusRepository extends JpaRepository<LunchMenu, Integer> {

    Optional<LunchMenu> findAllByRestaurantAndDateAfter(Restaurant restaurant, LocalDate date);

    List<LunchMenu> findAllByDateAfter(LocalDate date);
}