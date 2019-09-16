package graduation.raitrest.service;

import graduation.raitrest.model.entities.Vote;
import graduation.raitrest.model.to.Rating;
import graduation.raitrest.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static graduation.raitrest.util.DateTimeUtil.adjustEndDateTime;
import static graduation.raitrest.util.DateTimeUtil.adjustStartDateTime;
import static graduation.raitrest.util.ValidationUtil.*;


@Service
public class VoteService {

    private final VoteRepository voteRepository;

    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public Vote get(int id) {
        return checkNotFoundWithId(voteRepository.get(id), id);
    }

    public Vote get(int id, int userId) {
        return checkNotFoundWithId(voteRepository.get(id, userId), id);
    }

    public List<Rating> getRating(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Assert.notNull(startDateTime, "startDateTime must not be null");
        Assert.notNull(endDateTime, "endDateTime  must not be null");
        return voteRepository.getRating(startDateTime, endDateTime);
    }

    public List<Rating> getTodayRating() {
        //  return getRating(LocalDateTime.of(LocalDate.now(), LocalTime.MIN),LocalDateTime.of(LocalDate.now(), LocalTime.MAX));
        return getRating(adjustStartDateTime(LocalDate.now()), adjustEndDateTime(LocalDate.now()));
    }

    public List<Rating> getAllRating() {
        return getRating(adjustStartDateTime(null), adjustEndDateTime(null));
    }

    public List<Vote> getAll() {
        return voteRepository.getAll();
    }

    public List<Vote> getAll(int userId) {
        return voteRepository.getAll(userId);
    }

    private List<Vote> filterByDateTime(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        Assert.notNull(startDate, "startDateTime must not be null");
        Assert.notNull(endDate, "endDateTime  must not be null");
        return voteRepository.filterByDateTime(startDate, endDate, userId);
    }

    public List<Vote> filterByDate(LocalDate startDate, LocalDate endDate, int userId) {
        return filterByDateTime(adjustStartDateTime(startDate), adjustEndDateTime(endDate), userId);
    }

    List<Vote> filterByDateTime(LocalDateTime startDate, LocalDateTime endDate) {
        Assert.notNull(startDate, "startDateTime must not be null");
        Assert.notNull(endDate, "endDateTime  must not be null");
        return voteRepository.filterByDateTime(startDate, endDate);
    }
    public List<Vote> filterByDate(LocalDate startDate, LocalDate endDate) {
        return filterByDateTime(adjustStartDateTime(startDate), adjustEndDateTime(endDate));
    }

    public Vote getTodayVote(int userID) {
        List<Vote> votes = filterByDate(LocalDate.now(), LocalDate.now(), userID);
        return   DataAccessUtils.singleResult(votes);
    }

    public Vote create(Vote vote, int userId) {
        Assert.notNull(vote, "vote must not be null");
        Assert.notNull(vote.getRestaurant(), "vote#restaurant must not be null");
        checkDateTime(vote.getDateTime());
        return voteRepository.save(vote, userId);
    }
    public Vote create(Vote vote, int restaurant , int userId) {
        Assert.notNull(vote, "vote must not be null");
        checkDateTime(vote.getDateTime());
        return voteRepository.save(vote, restaurant, userId);
    }
    public void update(Vote vote, int userId) {
        Assert.notNull(vote, "vote must not be null");
        Assert.notNull(vote.getRestaurant(), "vote#restaurant must not be null");
        checkDateTime(vote.getDateTime());
        checkNotFoundWithId(voteRepository.save(vote, userId),vote.getId());
    }

    public void update(Vote vote,int restaurantID, int userId) {
        Assert.notNull(vote, "vote must not be null");
        checkDateTime(vote.getDateTime());
        checkNotFoundWithId(voteRepository.save(vote,restaurantID, userId),vote.getId());
    }
    public void delete(int id, int userId) {

        checkNotFoundWithId(voteRepository.delete(id, userId), id);
    }

    public void delete(int id) {

        checkNotFoundWithId(voteRepository.delete(id), id);
    }


}
