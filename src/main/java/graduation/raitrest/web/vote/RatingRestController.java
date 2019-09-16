package graduation.raitrest.web.vote;

import graduation.raitrest.model.entities.Vote;
import graduation.raitrest.model.to.Rating;
import graduation.raitrest.model.to.VoteTo;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = RatingRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RatingRestController  extends AbstractVoteController  {
    public static final String REST_URL = "/rest/profile/rating";
    @Override
    @GetMapping("/all")
    public List<Rating> getRatingRestaurants(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        return super.getRatingRestaurants(startDate, endDate);
    }

    @Override
    @GetMapping()
    public List<Rating> getTodayRatingRestaurants() {
        return super.getTodayRatingRestaurants();
    }



}
