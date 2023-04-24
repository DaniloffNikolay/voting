package ru.danilov.voting.voting.repositories.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.danilov.voting.voting.models.restaurant.LunchMenu;

public interface LunchMenusRepository extends JpaRepository<LunchMenu, Integer> {
}
