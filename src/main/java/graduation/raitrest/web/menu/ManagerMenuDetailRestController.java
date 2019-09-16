package graduation.raitrest.web.menu;

import graduation.raitrest.model.entities.MenuDetails;
import graduation.raitrest.model.to.MenuDetailTo;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static graduation.raitrest.util.Util.menuDetail_2_MenuDetailTo;

@RestController
@RequestMapping(value = ManagerMenuDetailRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ManagerMenuDetailRestController extends AbstractMenuDetailController {
    public static final String REST_URL = "/rest/manager/menu";

//    @Override
    @GetMapping("/{id}")
    public MenuDetailTo getTo(@PathVariable int id) {
        return menuDetail_2_MenuDetailTo(super.get(id));
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PostMapping(/*value = "/{restaurantID}",*/consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MenuDetailTo> createWithLocation(@Valid @RequestBody MenuDetailTo menuDetailsTo /*,@PathVariable int restaurantID*/) {
        MenuDetails created = new MenuDetails(null,
                menuDetailsTo.getTypeDish(),
                menuDetailsTo.getDescription(),
                menuDetailsTo.getQuantity() ,
                menuDetailsTo.getPrice(),
                LocalDateTime.now() // menuDetailsTo.getDateTime()

        );

         created = super.create(created,menuDetailsTo.getRestaurantID());

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body( menuDetail_2_MenuDetailTo(created));
    }


    @PutMapping(/*value = "/{id}",*/ consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody MenuDetailTo menuDetailsTo/*, @PathVariable int id*/) {
        MenuDetails menuDetails = super.get(menuDetailsTo.id());
        menuDetails.setTypeDish(menuDetailsTo.getTypeDish());
        menuDetails.setDescription(menuDetailsTo.getDescription());
        menuDetails.setQuantity(menuDetailsTo.getQuantity());
        menuDetails.setPrice(menuDetailsTo.getPrice());
        // date update
        menuDetails.setDateTime(new Date());
        // manager and restaurant do not touch

        super.update(menuDetails, menuDetailsTo.id());
    }




}
