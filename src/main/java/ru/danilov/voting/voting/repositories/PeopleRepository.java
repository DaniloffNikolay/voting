package ru.danilov.voting.voting.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.danilov.voting.voting.models.Person;

public interface PeopleRepository extends JpaRepository<Person, Integer> {
}
