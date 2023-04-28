package ru.danilov.voting.voting.repositories.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.danilov.voting.voting.models.restaurant.Dish;

import java.util.Optional;

public interface DishesRepository extends JpaRepository<Dish, Integer> {

    Optional<Dish> findFirstByNameIs(String name);
}
