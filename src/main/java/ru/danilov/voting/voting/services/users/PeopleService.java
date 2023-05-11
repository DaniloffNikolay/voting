package ru.danilov.voting.voting.services.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danilov.voting.voting.models.Person;
import ru.danilov.voting.voting.repositories.PeopleRepository;
import ru.danilov.voting.voting.util.exceptions.PersonNotFoundException;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findById(int id) {
        return peopleRepository.findById(id).orElseThrow(PersonNotFoundException::new);
    }

    @Transactional
    public Person save(Person person) {
        return peopleRepository.save(person);
    }

    @Transactional
    public void delete(Person person) {
        peopleRepository.delete(person);
    }

    @Transactional
    public void deleteAll() {
        peopleRepository.deleteAll();
    }
}
