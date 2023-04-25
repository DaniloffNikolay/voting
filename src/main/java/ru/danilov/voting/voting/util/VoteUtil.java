package ru.danilov.voting.voting.util;

import ru.danilov.voting.voting.models.Person;
import ru.danilov.voting.voting.models.Vote;
import ru.danilov.voting.voting.models.restaurant.Restaurant;
import ru.danilov.voting.voting.services.VotesService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

public class VoteUtil {

    private static Vote setVote(Optional<Person> person, Optional<Restaurant> restaurant) {
        if (person.isPresent() && person.get().getRole().equals("guest") && restaurant.isPresent()) {
            return new Vote(LocalDateTime.now(), person.get(), restaurant.get());
        }
        return null;
    }

    private static boolean checkTimeVote(Vote vote) {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(16, 0,0));
        if (vote.getDateTime().isAfter(localDateTime)) {
            return true;
        }
        return false;
    }

    public static void checkVote(Optional<Person> person, Optional<Restaurant> restaurant, VotesService votesService) {
        Vote vote = VoteUtil.setVote(person, restaurant);
        if (vote != null) {
            Optional<Vote> checkVote = votesService.findAllTodayVotesWhereId(vote.getPerson());

            if (checkVote.isPresent()) {
                if (!VoteUtil.checkTimeVote(vote)) {
                    Vote resultVote = checkVote.get();
                    resultVote.setRestaurant(vote.getRestaurant());
                    resultVote.setDateTime(vote.getDateTime());

                    votesService.save(resultVote);
                }
            } else {
                votesService.save(vote);
            }
        } else {
            //admin vote or not enough data
        }

    }
}
