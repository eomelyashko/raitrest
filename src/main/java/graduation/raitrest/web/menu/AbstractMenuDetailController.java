package graduation.raitrest.web.menu;

import graduation.raitrest.model.entities.MenuDetails;
import graduation.raitrest.model.to.MenuDetailTo;
import graduation.raitrest.service.MenuDetailsService;
import graduation.raitrest.web.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static graduation.raitrest.util.ValidationUtil.assureIdConsistent;
import static graduation.raitrest.util.ValidationUtil.checkNew;

public class AbstractMenuDetailController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected MenuDetailsService service;

    public MenuDetails get(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("get menu {} for manager/user {}", id, userId);
        return service.get(id, userId);
    }

    public void delete(int id) {
        int managerID = SecurityUtil.authUserId();
        log.info("delete menu {} for manager {}", id, managerID);
        service.delete(id, managerID);
    }

    public List<MenuDetailTo> getAll() {
        int userId = SecurityUtil.authUserId();
        log.info("getAllByUser for user {}", userId);
        return service.getAll();
    }
    public List<MenuDetails> getAllCurrentDay() {
        int userId = SecurityUtil.authUserId();
        log.info("getAllCurrentDay for user {}", userId);
        return service.getFilterByDate(LocalDate.now(),LocalDate.now());
    }



    public MenuDetails create(MenuDetails menuDetails,int restaurantID) {
        int managerId = SecurityUtil.authUserId();
        checkNew(menuDetails);
        log.info("create {} for manager {}", menuDetails, managerId);
        return service.create(menuDetails, restaurantID,managerId);
    }


    public void update(MenuDetails menuDetails, int id) {
        int managerId = SecurityUtil.authUserId();
        assureIdConsistent(menuDetails, id);
        MenuDetails md = service.get(id, managerId);
        Integer restaurantID = md.getRestaurant().getId();
        Objects.requireNonNull(restaurantID,"restaurant not found");
        log.info("update {} for manger {}", menuDetails, managerId);
        service.update(menuDetails,restaurantID, managerId);
    }

}
