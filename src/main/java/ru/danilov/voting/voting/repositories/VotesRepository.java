package ru.danilov.voting.voting.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.danilov.voting.voting.models.Vote;

public interface VotesRepository extends JpaRepository<Vote, Integer> {
}
