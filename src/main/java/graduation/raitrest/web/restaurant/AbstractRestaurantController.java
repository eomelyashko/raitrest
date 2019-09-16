package graduation.raitrest.web.restaurant;

import graduation.raitrest.model.entities.Restaurant;
import graduation.raitrest.model.to.RestaurantTo;
import graduation.raitrest.service.RestaurantService;
import graduation.raitrest.web.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static graduation.raitrest.util.ValidationUtil.assureIdConsistent;
import static graduation.raitrest.util.ValidationUtil.checkNew;

public class AbstractRestaurantController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected  RestaurantService service;

    public Restaurant get(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("get restaurant {} for user {}", id, userId);
        return service.get(id, userId);
    }

    public void delete(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("delete restaurant {} for user {}", id, userId);
        service.delete(id, userId);
    }

    public List<RestaurantTo> getAll() {
        int userId = SecurityUtil.authUserId();
        log.info("getAllByUser restaurants  for user {}", userId);
        return service.getAll();
    }
    public List<Restaurant> getAll(int manager) {
        int userId = SecurityUtil.authUserId();
        log.info("getAllByUser restaurants by manager  for user {}", userId);
        return service.getAll(manager);
    }

    public Restaurant create(Restaurant restaurant) {
        int managerId = SecurityUtil.authUserId();
        checkNew(restaurant);
        log.info("create {} for user {}", restaurant, managerId);
        return service.create(restaurant, managerId);
    }

    public void update(Restaurant restaurant, int id) {
        int userId = SecurityUtil.authUserId();
        assureIdConsistent(restaurant, id);
        log.info("update {} for user {}", restaurant, userId);
        service.update(restaurant, userId);
    }


}
