package graduation.raitrest.web.vote;

import graduation.raitrest.model.entities.Vote;
import graduation.raitrest.model.to.Rating;
import graduation.raitrest.model.to.VoteTo;
import graduation.raitrest.util.exception.IllegalRequestDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static graduation.raitrest.util.VoteUtil.Vote_2_VoteTo;

@RestController
@RequestMapping(value = VoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteRestController extends AbstractVoteController {
    public static final String REST_URL = "/rest/profile/votes";

   // @Override
    @GetMapping("/{id}")
    public VoteTo getTo(@PathVariable int id) {

        Vote vote = super.get(id);

        return Vote_2_VoteTo(vote);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @Override
    @GetMapping
    public List<Vote> getAllByUser() {
        return super.getAllByUser();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody VoteTo voteTo, @PathVariable int id) {
        Vote vote = super.get(id);
        // when updating set current time
        vote.setDateTime(LocalDateTime.now());

        super.update(vote, voteTo.getRestaurantID(), id); // voteTo.id().
    }
/*
    If user votes again the same day:
    If it is before 11:00 we asume that he changed his mind.
    If it is after 11:00 then it is too late, vote can't be changed



    */

    // by vote
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoteTo> createByVote(@Valid @RequestBody VoteTo voteTo) {
        Vote created = getTodayByUserID();
        if (created != null) {
            update(created, voteTo.getRestaurantID(), created.getId());
        } else {
            created = super.create(voteTo);
        }
        voteTo.setId(created.getId());
        voteTo.setDateTime(created.getDateTime());

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();


        return ResponseEntity.created(uriOfNewResource).body(voteTo);
    }

    // by restaurant ID
    @PostMapping(value = "/restaurant/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoteTo> createByRestaurantID(@PathVariable int id) {
        // get today vote
        Vote created = getTodayByUserID();
        if (created != null) {
            update(created, id, created.getId());
        } else {
            created = super.create(new Vote(), id);
        }

        VoteTo voteTo = new VoteTo();
        voteTo.setRestaurantID(id);
        voteTo.setId(created.getId());
        voteTo.setDateTime(created.getDateTime());

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();


        return ResponseEntity.created(uriOfNewResource).body(voteTo);
    }


}
