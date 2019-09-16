package graduation.raitrest.service;

import graduation.raitrest.model.entities.Restaurant;
import graduation.raitrest.model.to.RestaurantTo;
import graduation.raitrest.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static graduation.raitrest.util.Util.menuDetail_2_MenuDetailTo;
import static graduation.raitrest.util.Util.restaurant_2_RestaurantTo;
import static graduation.raitrest.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantService {

    @Autowired
    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<RestaurantTo> getAll() {
        return restaurant_2_RestaurantTo(restaurantRepository.getAll());
    }

    public List<Restaurant> getAll(int manager_id) {
        return restaurantRepository.getAll(manager_id);
    }

    public Restaurant get(int id) {
        return checkNotFoundWithId(restaurantRepository.get(id), id);
    }

    public Restaurant get(int id, int userId) {
        return checkNotFoundWithId(restaurantRepository.get(id, userId), id);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(restaurantRepository.delete(id, userId), id);
    }

    public void delete(int id) {
        checkNotFoundWithId(restaurantRepository.delete(id), id);
    }

    public void update(Restaurant restaurant, int userId) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithId(restaurantRepository.save(restaurant, userId), restaurant.getId());
    }

    public Restaurant create(Restaurant restaurant, int userId) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return restaurantRepository.save(restaurant, userId);
    }


}
