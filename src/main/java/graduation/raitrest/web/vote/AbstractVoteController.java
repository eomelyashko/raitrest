package graduation.raitrest.web.vote;

import graduation.raitrest.model.entities.Vote;
import graduation.raitrest.model.to.Rating;
import graduation.raitrest.model.to.VoteTo;
import graduation.raitrest.service.VoteService;
import graduation.raitrest.web.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static graduation.raitrest.util.DateTimeUtil.adjustEndDateTime;
import static graduation.raitrest.util.DateTimeUtil.adjustStartDateTime;
import static graduation.raitrest.util.ValidationUtil.assureIdConsistent;
import static graduation.raitrest.util.ValidationUtil.checkNew;

public class AbstractVoteController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    protected VoteService service;

    public Vote get(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("get vote {} for user {}", id, userId);
        return service.get(id, userId);
    }

    public Vote getTodayByUserID() {
        int userId = SecurityUtil.authUserId();
        log.info("getTodayByUserID for user {}", userId);
        return service.getTodayVote(userId);
    }

    public void delete(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("delete vote {} for user {}", id, userId);
        service.delete(id, userId);
    }

    public List<Vote> getAllByUser() {
        int userId = SecurityUtil.authUserId();
        log.info("getAllByUser votes for user {}", userId);
        return service.getAll(userId);
    }

    public Vote create(Vote vote, int restaurantID) {
        int userId = SecurityUtil.authUserId();
        checkNew(vote);
        log.info("create {} for user {} , restaurant {}", vote, userId, restaurantID);
        return service.create(vote, restaurantID, userId);
    }

    public Vote create(VoteTo voteTo) {
        int userId = SecurityUtil.authUserId();
        checkNew(voteTo);
        log.info("create {} for user {} , restaurant {}", voteTo, userId, voteTo.getRestaurantID());
        Vote newVote = new Vote();
        return service.create(newVote, voteTo.getRestaurantID(), userId);
    }


    public void update(Vote vote, int id) {
        int userId = SecurityUtil.authUserId();
        assureIdConsistent(vote, id);
        log.info("update {} for user {}", vote, userId);
        service.update(vote, userId);
    }

    public void update(Vote vote, int resaurantID, int id) {
        int userId = SecurityUtil.authUserId();
        assureIdConsistent(vote, id);
        log.info("update {} for user {}", vote, userId);
        service.update(vote, resaurantID, userId);
    }

    public List<Rating> getRatingRestaurants(LocalDate startDate, LocalDate endDate) {
        int userId = SecurityUtil.authUserId();
        log.info("getBetween dates({} - {}) time({} - {}) for user {}", startDate, endDate, userId);
        return service.getRating(adjustStartDateTime(startDate), adjustEndDateTime(endDate));

    }

    public List<Rating> getTodayRatingRestaurants() {
        int userId = SecurityUtil.authUserId();
        log.info("getBetween dates({} - {}) time({} - {}) for user {}");
        return service.getTodayRating();


    }

}