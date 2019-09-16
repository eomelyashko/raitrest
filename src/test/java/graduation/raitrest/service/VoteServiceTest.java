package graduation.raitrest.service;

import graduation.raitrest.model.entities.Vote;
import graduation.raitrest.model.to.Rating;
import graduation.raitrest.util.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static graduation.raitrest.RestoranTestData.RESTAURANT_ID;
import static graduation.raitrest.RestoranTestData.RESTAURANT_PEARL;
import static graduation.raitrest.UserTestData.*;
import static graduation.raitrest.VotesTestData.assertMatch;
import static graduation.raitrest.VotesTestData.*;
import static graduation.raitrest.util.ValidationUtil.MAX_TIME;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class VoteServiceTest extends AbstractServiceTest {
    @Autowired
    public VoteService voteService;

    //  Assumptions.assumeTrue(LocalTime.now().before, "Validation time");

    @Test
     void get() {
        Vote vote = voteService.get(VOTES_1_YESTERDAY.id());
        assertMatch(vote, VOTES_1_YESTERDAY);

        vote = voteService.get(VOTES_1_TODAY.id(), USER_ID);
        assertMatch(vote, VOTES_1_TODAY);

        vote = voteService.getTodayVote(VOTES_2_TODAY.getUser().id());
        assertMatch(vote, VOTES_2_TODAY);

        vote = voteService.getTodayVote(USER_3_ID);

        Assertions.assertNull(vote);


    }

    @Test
     void getRating() {
        List<Rating> currentRating = voteService.getTodayRating();
        Assertions.assertEquals(2, currentRating.size());

        currentRating = voteService.getAllRating();
        Assertions.assertEquals(4, currentRating.size());

    }

    @Test
     void getAll() {
        List<Vote> allVotes = voteService.getAll();
        List<Vote> collect = VOTE_LIST.stream().sorted((o1, o2) -> o2.getDateTime().toLocalDate().compareTo(o1.getDateTime().toLocalDate())).collect(Collectors.toList());
        assertMatch(allVotes, collect);

        allVotes = voteService.getAll(USER_ID);
        collect = VOTE_LIST.stream().filter(vote -> vote.getUser().getId() == USER_ID)
                .sorted((o1, o2) -> o2.getDateTime().toLocalDate().compareTo(o1.getDateTime().toLocalDate()))
                .collect(Collectors.toList());
        assertMatch(allVotes, collect);
    }

    @Test
     void create() {
        Vote newVote = new Vote();
        // For test set time before 11.00
        newVote.setDateTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 20)));
        Vote voteCreate = voteService.create(newVote, RESTAURANT_ID, USER_3_ID);
        Integer id = voteCreate.getId();
        newVote.setId(id);
        assertMatch(newVote, voteService.get(id));
    }

    @Test
    void createWithTime() {
        Assumptions.assumeTrue(checkDateTimeIsBefore(LocalDateTime.now()), "Validation time");
        Vote newVote = new Vote();
        Vote voteCreate = voteService.create(newVote, RESTAURANT_ID, USER_3_ID);
        Integer id = voteCreate.getId();
        newVote.setId(id);
        assertMatch(newVote, voteService.get(id));
    }


    @Test
    void createWrongTimeWithSetTime() {
        Assumptions.assumeTrue(checkDateTimeIsAfter(LocalDateTime.now()), "Validation time");
        Vote newVote = new Vote();
        // For test set time after 11.00
        newVote.setDateTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 20)));
        assertThrows(IllegalArgumentException.class ,() ->
                voteService.create(newVote, RESTAURANT_ID, USER_3_ID));

    }
    @Test
    void createWrongTime() {
        Assumptions.assumeTrue(checkDateTimeIsAfter(LocalDateTime.now()), "Validation time, run after " + MAX_TIME);
        Vote newVote = new Vote();
        assertThrows(IllegalArgumentException.class ,() ->
                voteService.create(newVote, RESTAURANT_ID, USER_3_ID));

    }


    @Test
     void createWithRestaurant() {
        Vote newVote = getCreated();
        // голос уже с рестораном внутри
        Vote voteCreate = voteService.create(newVote, USER_3_ID);
        Integer id = voteCreate.getId();
        newVote.setId(id);
        assertMatch(newVote, voteService.get(id));

    }

    @Test
     void update() {
        Vote voteUpdate = voteService.get(VOTES_2_TODAY.id(), USER_1_ID);
        voteUpdate.setRestaurant(RESTAURANT_PEARL);
        voteService.update(voteUpdate , USER_1_ID);
        assertMatch(voteUpdate, voteService.get(voteUpdate.getId()));
      //  RestoranTestData.assertMatch(voteUpdate.getRestaurant(), voteService.get(voteUpdate.getId()).getRestaurant());
        List<Rating> currentRating = voteService.getTodayRating();
        Assertions.assertEquals(1, currentRating.size());


    }

    @Test
     void delete()  {
        Vote  vote = voteService.getTodayVote(USER_2_ID);
        Assertions.assertNotNull(vote);;
        voteService.delete(VOTES_3_TODAY.id(), USER_2_ID);
        vote = voteService.getTodayVote(USER_2_ID);
        Assertions.assertNull(vote);


    }

    @Test
     void deleteNotFound()  {

        assertThrows(NotFoundException.class ,() ->
        voteService.delete(1, MANAGER_ID));
    }


}