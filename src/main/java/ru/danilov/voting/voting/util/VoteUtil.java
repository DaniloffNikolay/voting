package ru.danilov.voting.voting.util;

import org.springframework.web.bind.annotation.PathVariable;
import ru.danilov.voting.voting.models.Person;
import ru.danilov.voting.voting.models.Vote;
import ru.danilov.voting.voting.models.restaurant.Restaurant;
import ru.danilov.voting.voting.services.VotesService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class VoteUtil {

    public static Vote setVote(Optional<Person> person, Optional<Restaurant> restaurant) {
        if (person.isPresent() && person.get().getRole().equals("guest") && restaurant.isPresent()) {
            return new Vote(LocalDateTime.now(), person.get(), restaurant.get());
        }
        return null;
    }

    public static boolean checkTimeVote(Vote vote, Vote checkVote) {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(16, 0,0));
        if (vote.getDateTime().isAfter(localDateTime)) {
            return true;
        }
        return false;
    }
}
