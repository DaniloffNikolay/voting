package ru.danilov.voting.voting.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ru.danilov.voting.voting.util.exceptions.ValidationFailedException;

import java.util.List;

/**
 * User: Nikolai Danilov
 * Date: 11.05.2023
 */
public class BindingResultUtil {

    public static void getException(BindingResult bindingResult) throws ValidationFailedException {
        StringBuilder errorMessage = new StringBuilder();

        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            errorMessage.append(error.getField())
                    .append(" - ")
                    .append(error.getDefaultMessage())
                    .append(";");
        }

        throw new ValidationFailedException(errorMessage.toString());
    }
}
