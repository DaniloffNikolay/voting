package ru.danilov.voting.voting.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.danilov.voting.voting.models.Person;
import ru.danilov.voting.voting.models.Vote;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VotesRepository extends JpaRepository<Vote, Integer> {

    public Optional<Vote> findFirstByDateTimeAfterAndPerson(LocalDateTime localDateTime, Person person);
}
