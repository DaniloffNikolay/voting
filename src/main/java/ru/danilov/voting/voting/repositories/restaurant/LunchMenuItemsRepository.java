package ru.danilov.voting.voting.repositories.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.danilov.voting.voting.models.restaurant.LunchMenuItem;

public interface LunchMenuItemsRepository extends JpaRepository<LunchMenuItem, Integer> {
}
