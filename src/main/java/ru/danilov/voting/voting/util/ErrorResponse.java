package ru.danilov.voting.voting.util;

/**
 * User: Nikolai Danilov
 * Date: 11.05.2023
 */
public class ErrorResponse {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
