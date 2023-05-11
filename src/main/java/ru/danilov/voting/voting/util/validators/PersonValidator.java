package ru.danilov.voting.voting.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.danilov.voting.voting.models.Person;
import ru.danilov.voting.voting.services.users.PersonDetailsService;

/**
 * User: Nikolai Danilov
 * Date: 11.05.2023
 */
@Component
public class PersonValidator implements Validator {

    private final PersonDetailsService personDetailsService;

    @Autowired
    public PersonValidator(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;

        try {
            personDetailsService.loadUserByUsername(person.getName());
        } catch (UsernameNotFoundException ignored) {
            return;
        }

        errors.rejectValue("name", "", "A person with this username already exists");
    }
}

