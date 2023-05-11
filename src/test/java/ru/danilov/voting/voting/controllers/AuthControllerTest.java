package ru.danilov.voting.voting.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.danilov.voting.voting.Util;
import ru.danilov.voting.voting.dto.PersonDTO;
import ru.danilov.voting.voting.services.users.PeopleService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * User: Nikolai Danilov
 * Date: 11.05.2023
 */
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {
    private final ObjectMapper objectMapper;
    private final MockMvc mockMvc;
    private final PeopleService peopleService;

    @Autowired
    public AuthControllerTest(ObjectMapper objectMapper, MockMvc mockMvc, PeopleService peopleService) {
        this.objectMapper = objectMapper;
        this.mockMvc = mockMvc;
        this.peopleService = peopleService;
    }

    @AfterEach
    public void resetDb() {
        peopleService.deleteAll();
    }

    @Test
    public void postRegistrationThenGetJWTToken() throws Exception {
        PersonDTO personDTO = Util.createTestLoginPersonDTO();

        mockMvc.perform(post("/auth/registration")
                        .content(objectMapper.writeValueAsString(personDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("jwt-token").isNotEmpty());
    }

    @Test
    public void postLoginThenGetJWTToken() throws Exception {
        PersonDTO personDTO = Util.createTestLoginPersonDTO();
        mockMvc.perform(post("/auth/registration")
                .content(objectMapper.writeValueAsString(personDTO))
                .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(post("/auth/login")
                        .content(objectMapper.writeValueAsString(personDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("jwt-token").isNotEmpty());
    }


}
