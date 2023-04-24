package ru.danilov.voting.voting.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danilov.voting.voting.models.Vote;
import ru.danilov.voting.voting.repositories.VotesRepository;

import java.util.List;

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
}
