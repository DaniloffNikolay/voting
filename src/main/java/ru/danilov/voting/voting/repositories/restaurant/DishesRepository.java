package ru.danilov.voting.voting.repositories.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.danilov.voting.voting.models.restaurant.Dish;

public interface DishesRepository extends JpaRepository<Dish, Integer> {
}
