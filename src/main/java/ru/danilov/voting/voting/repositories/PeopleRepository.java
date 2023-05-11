package ru.danilov.voting.voting.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.danilov.voting.voting.models.Person;

import java.util.Optional;

public interface PeopleRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByName(String username);
}
