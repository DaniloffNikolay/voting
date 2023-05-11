package ru.danilov.voting.voting.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.danilov.voting.voting.dto.AuthenticationDTO;
import ru.danilov.voting.voting.dto.PersonDTO;
import ru.danilov.voting.voting.models.Person;
import ru.danilov.voting.voting.security.JWTUtil;
import ru.danilov.voting.voting.services.users.RegistrationService;
import ru.danilov.voting.voting.util.PersonValidator;

import javax.validation.Valid;
import java.util.Map;

/**
 * User: Nikolai Danilov
 * Date: 11.05.2023
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final RegistrationService registrationService;
    private final PersonValidator personValidator;
    private final JWTUtil jwtUtil;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(RegistrationService registrationService, PersonValidator personValidator, JWTUtil jwtUtil, ModelMapper modelMapper, AuthenticationManager authenticationManager) {
        this.registrationService = registrationService;
        this.personValidator = personValidator;
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/registration")
    public Map<String, String> performRegistration(@RequestBody @Valid PersonDTO personDTO,
                                   BindingResult bindingResult) {
        Person person = convertToPerson(personDTO);


        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return Map.of("message", "error");

        registrationService.register(person);

        String token = jwtUtil.generateToken(person.getName());

        return Map.of("jwt-token", token);
    }

    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody AuthenticationDTO authenticationDTO) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        authenticationDTO.getName(),
                        authenticationDTO.getPassword());

        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            return Map.of("message", "Incorrect credentials!");
        }

        String token = jwtUtil.generateToken(authenticationDTO.getName());
        return Map.of("jwt-token", token);
    }

    public Person convertToPerson(PersonDTO personDTO) {
        return this.modelMapper.map(personDTO, Person.class);
    }
}
