package ru.danilov.voting.voting.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danilov.voting.voting.models.Person;
import ru.danilov.voting.voting.models.Vote;
import ru.danilov.voting.voting.repositories.VotesRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class VotesService {

    private final VotesRepository votesRepository;

    @Autowired
    public VotesService(VotesRepository votesRepository) {
        this.votesRepository = votesRepository;
    }

    public List<Vote> findAll() {
        return votesRepository.findAll();
    }

    public Optional<Vote> findAllTodayVotesWhereId(Person person) {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0,0));

        return votesRepository.findFirstByDateTimeAfterAndPerson(localDateTime, person);
    }
}
