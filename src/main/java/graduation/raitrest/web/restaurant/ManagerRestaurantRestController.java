package graduation.raitrest.web.restaurant;

import graduation.raitrest.model.entities.Restaurant;
import graduation.raitrest.model.to.RestaurantTo;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = ManagerRestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ManagerRestaurantRestController extends AbstractRestaurantController {
    public static final String REST_URL = "/rest/manager/restaurant";



    @GetMapping(value = "/manager/{id}")
    public List<Restaurant> getAllByManager(@PathVariable int id) {
        log.info("getAllByManager");
        return super.getAll(id);
    }

    @Override
    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

  //  @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody RestaurantTo restaurantTo, @PathVariable int id) {
        Restaurant restaurant = super.get(id);
        // id, manager and data - do not change
        restaurant.setName(restaurantTo.getName());
        restaurant.setAddress(restaurantTo.getAddress());
        restaurant.setOwner(restaurantTo.getOwner());
        restaurant.setDescription(restaurantTo.getDescription());

        super.update(restaurant, id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@Valid @RequestBody RestaurantTo restaurantTo) {
        Restaurant created = new Restaurant(restaurantTo.getName(),
                restaurantTo.getAddress(),
                restaurantTo.getOwner(),
                restaurantTo.getDescription(),
                null
                );
         created = super.create(created);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }





}
