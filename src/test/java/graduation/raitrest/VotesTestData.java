package graduation.raitrest;

import graduation.raitrest.model.entities.Restaurant;
import graduation.raitrest.model.entities.Vote;
import graduation.raitrest.model.to.Rating;
import graduation.raitrest.model.to.VoteTo;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static graduation.raitrest.RestoranTestData.*;
import static graduation.raitrest.TestUtil.readListFromJsonMvcResult;
import static graduation.raitrest.UserTestData.*;
import static graduation.raitrest.util.ValidationUtil.MAX_DATE_TIME;
import static org.assertj.core.api.Assertions.assertThat;

public class VotesTestData {
    public static final int VOTES_ID = 100013;
    public static final Vote VOTES_1_YESTERDAY = new Vote(VOTES_ID, LocalDateTime.now().minusDays(1l), USER, RESTAURANT_STAR);
    public static final Vote VOTES_2_YESTERDAY = new Vote(VOTES_ID + 1, LocalDateTime.now().minusDays(1l), USER_1, RESTAURANT_PEARL);
    public static final Vote VOTES_3_YESTERDAY = new Vote(VOTES_ID + 2, LocalDateTime.now().minusDays(1l), USER_2, RESTAURANT_SUPER_STAR);
    public static final Vote VOTES_4_YESTERDAY = new Vote(VOTES_ID + 3, LocalDateTime.now().minusDays(1l), USER_3, RESTAURANT_BLACK_PEARL);

    public static final Vote VOTES_1_TODAY = new Vote(VOTES_ID + 4, LocalDateTime.now(), USER, RESTAURANT_PEARL);
    public static final Vote VOTES_2_TODAY = new Vote(VOTES_ID + 5, LocalDateTime.now(), USER_1, RESTAURANT_SUPER_STAR);
    public static final Vote VOTES_3_TODAY = new Vote(VOTES_ID + 6, LocalDateTime.now(), USER_2, RESTAURANT_PEARL);
    //   откючил т.к. нужно для тестрирвоания
    //   public static final Vote VOTES_4_TODAY = new Vote(VOTES_ID + 7, LocalDateTime.now().minusDays(1l), USER, RESTAURANT_STAR);

    public static final List<Vote> VOTE_LIST = List.of(VOTES_1_YESTERDAY, VOTES_2_YESTERDAY, VOTES_3_YESTERDAY, VOTES_4_YESTERDAY, VOTES_1_TODAY,
            VOTES_2_TODAY, VOTES_3_TODAY);

    public static Vote getCreated() {
        return new Vote(LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 20)), USER_3, RESTAURANT_STAR);
    }

    public static void assertMatch(Vote actual, Vote expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "dateTime", "user", "restaurant"); //,"user","restaurant"
    }

    public static void assertMatch(Iterable<Vote> actual, Vote... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Vote> actual, Iterable<Vote> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("dateTime", "user", "restaurant").isEqualTo(expected);

    }
    public static ResultMatcher contentJson(Vote... expected) {
        return contentJson(List.of(expected));
    }

    public static ResultMatcher contentJson(Iterable<Vote> expected) {
        return result -> assertThat(readListFromJsonMvcResult(result, Vote.class)).isEqualTo(expected);
    }
    public static ResultMatcher contentJson(Rating... expected) {
        return contentJsonTo(List.of(expected));
    }

    public static ResultMatcher contentJsonTo(Iterable<Rating> expected) {
        return result -> assertThat(readListFromJsonMvcResult(result, Rating.class)).isEqualTo(expected);
    }

    public static void assertMatchTo(VoteTo actual, VoteTo expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "dateTime", "user", "restaurant"); //,"user","restaurant"
    }


    public static boolean checkDateTimeIsAfter(LocalDateTime dateTime) {
//        if (dateTime.isAfter(MAX_DATE_TIME)) {
//            throw new IllegalArgumentException("Time must be before " + MAX_TIME);
//        }
        return dateTime.isAfter(MAX_DATE_TIME);
    }

    public static boolean checkDateTimeIsBefore(LocalDateTime dateTime) {
//        if (dateTime.isAfter(MAX_DATE_TIME)) {
//            throw new IllegalArgumentException("Time must be before " + MAX_TIME);
//        }
        return dateTime.isBefore(MAX_DATE_TIME);
    }
}
