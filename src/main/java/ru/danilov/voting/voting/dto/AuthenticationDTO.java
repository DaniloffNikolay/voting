package ru.danilov.voting.voting.dto;

/**
 * User: Nikolai Danilov
 * Date: 11.05.2023
 */
public class AuthenticationDTO {

    private String name;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
