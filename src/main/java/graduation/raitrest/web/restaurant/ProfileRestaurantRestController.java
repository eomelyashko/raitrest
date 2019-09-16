package graduation.raitrest.web.restaurant;

import graduation.raitrest.AuthorizedUser;
import graduation.raitrest.model.entities.Restaurant;
import graduation.raitrest.model.to.RestaurantTo;
import graduation.raitrest.web.SecurityUtil;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static graduation.raitrest.util.Util.restaurant_2_RestaurantTo;

@RestController
@RequestMapping(value = ProfileRestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileRestaurantRestController extends AbstractRestaurantController {

    public static final String REST_URL = "/rest/profile/restaurant";

    //@Override
    @GetMapping
    public List<RestaurantTo> getAll(@AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("getAllRestaurants for user {}",authUser.getId());
        return super.getAll();
    }


    @GetMapping("/{id}")
    public RestaurantTo getTo(@PathVariable int id,@AuthenticationPrincipal AuthorizedUser authUser) {
              log.info("get restaurant {} for user {}", id, authUser.getId());

        return  restaurant_2_RestaurantTo(service.get(id));
    }

}
