package graduation.raitrest.web.menu;

import graduation.raitrest.AuthorizedUser;
import graduation.raitrest.model.entities.MenuDetails;
import graduation.raitrest.model.to.MenuDetailTo;
import graduation.raitrest.web.SecurityUtil;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import static graduation.raitrest.util.Util.menuDetail_2_MenuDetailTo;

@RestController
@RequestMapping(value = ProfileMenuDetailRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileMenuDetailRestController extends AbstractMenuDetailController {
    public static final String REST_URL = "/rest/profile/menu";

   // @Override
    @GetMapping("/all")
    public List<MenuDetailTo> getAll(@AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("get all menu   for user {}",  authUser.getId());
        return super.getAll();
    }


    @GetMapping()
    public List<MenuDetailTo> getAllCurrentDayTo(@AuthenticationPrincipal AuthorizedUser authUser) {

        log.info("get menu today  for user {}",  authUser.getId());

        return  menuDetail_2_MenuDetailTo(service.getFilterByDate(LocalDate.now(),LocalDate.now()));
    }

    @GetMapping("/restaurant/{id}")
    public  List<MenuDetailTo> getTodayByRestaurantID(@PathVariable int id,@AuthenticationPrincipal AuthorizedUser authUser) {

        log.info("get menu today by restaurantID  for user {}",  authUser.getId());

        return  menuDetail_2_MenuDetailTo(service.getFilterByDateByRestaurant(LocalDate.now(),LocalDate.now(),id));
    }

}
