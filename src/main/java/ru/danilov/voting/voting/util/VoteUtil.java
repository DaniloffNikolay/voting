package ru.danilov.voting.voting.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.danilov.voting.voting.VotingApplication;
import ru.danilov.voting.voting.models.Person;
import ru.danilov.voting.voting.models.Vote;
import ru.danilov.voting.voting.models.restaurant.Restaurant;
import ru.danilov.voting.voting.services.VotesService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

public class VoteUtil {

    private static final Logger log = LoggerFactory.getLogger(VotingApplication.class);

    private static Vote setVote(Optional<Person> person, Optional<Restaurant> restaurant) {
        if (person.isPresent() && person.get().getRole().equals("guest") && restaurant.isPresent()) {
            return new Vote(LocalDateTime.now(), person.get(), restaurant.get());
        }
        return null;
    }

    private static boolean checkTimeVote(Vote vote) {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 0,0));
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
                    log.info("voice is re-recorded");
                    Vote resultVote = checkVote.get();
                    resultVote.setRestaurant(vote.getRestaurant());
                    resultVote.setDateTime(vote.getDateTime());

                    votesService.save(resultVote);
                } else {
                    log.info("vote has already been counted");
                }
            } else {
                log.info("voice is being recorded");
                votesService.save(vote);
            }
        } else {
            log.info("admin vote or not enough data");
        }
    }
}
