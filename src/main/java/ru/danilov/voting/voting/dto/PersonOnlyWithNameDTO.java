package ru.danilov.voting.voting.dto;

import javax.validation.constraints.NotEmpty;

/**
 * User: Nikolai Danilov
 * Date: 11.05.2023
 */
public class PersonOnlyWithNameDTO {
    @NotEmpty(message = "Name should not be empty")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
