package ru.danilov.voting.voting.services.restaurant;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danilov.voting.voting.models.Person;
import ru.danilov.voting.voting.models.restaurant.LunchMenuItem;
import ru.danilov.voting.voting.repositories.restaurant.LunchMenuItemsRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class LunchMenuItemsService {

    private final LunchMenuItemsRepository lunchMenuItemsRepository;

    public LunchMenuItemsService(LunchMenuItemsRepository lunchMenuItemsRepository) {
        this.lunchMenuItemsRepository = lunchMenuItemsRepository;
    }

    public List<LunchMenuItem> findAll() {
        return lunchMenuItemsRepository.findAll();
    }
}
