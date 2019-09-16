package graduation.raitrest.service;

import graduation.raitrest.model.entities.Restaurant;
import graduation.raitrest.model.to.RestaurantTo;
import graduation.raitrest.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static graduation.raitrest.RestoranTestData.*;
import static graduation.raitrest.UserTestData.*;
import static graduation.raitrest.util.Util.restaurant_2_RestaurantTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RestaurantServiceTest extends AbstractServiceTest {
    @Autowired
    protected RestaurantService service;
    @Autowired
    protected UserService userService;

    @Test
     void getAll() {
        List<RestaurantTo> all = service.getAll();
        assertMatchTo(all, restaurant_2_RestaurantTo(restaurantList));
        // manager
//        all = service.getAll(MANAGER_ID);
//        assertMatch(all, RESTAURANT_STAR, RESTAURANT_SUPER_STAR);
        // user

    }

    @Test
     void getAllByManagerID() {
        List<Restaurant> all = service.getAll(MANAGER_ID);
        // admin have 2 restaurant
        assertMatch(all, RESTAURANT_STAR, RESTAURANT_SUPER_STAR);
        all = service.getAll(MANAGER_2_ID);
        assertMatch(all, RESTAURANT_BLACK_PEARL);
    }

    @Test
     void create() throws Exception {
        Restaurant newRestaurant = getCreated();
        Restaurant created = service.create(newRestaurant, MANAGER_ID);
        newRestaurant.setId(created.getId());

        assertMatch(newRestaurant, created);
        assertMatch(service.getAll(MANAGER_ID), RESTAURANT_STAR, RESTAURANT_SUPER_STAR,newRestaurant);

        Restaurant restaurant = service.get(newRestaurant.getId(), MANAGER_ID);
        assertMatch(newRestaurant, restaurant);

    }

    @Test
     void get() {
        Restaurant restaurant = service.get(RESTAURANT_ID);
        assertMatch(restaurant, RESTAURANT_STAR);
    }

    @Test
      void getByUserId() {
        Restaurant restaurant = service.get(RESTAURANT_ID , MANAGER_ID);
        assertMatch(restaurant, RESTAURANT_STAR);
    }

    @Test
     void delete() {
        service.delete(RESTAURANT_ID);
    //    assertMatch(service.getAll(), RESTAURANT_PEARL, RESTAURANT_SUPER_STAR, RESTAURANT_BLACK_PEARL);
        assertMatchTo(service.getAll(), restaurant_2_RestaurantTo(RESTAURANT_PEARL),
                restaurant_2_RestaurantTo(RESTAURANT_SUPER_STAR),
                restaurant_2_RestaurantTo(RESTAURANT_BLACK_PEARL));
    }

    @Test
     void deleteWithUserID() {
        service.delete(RESTAURANT_ID,MANAGER_ID);
        assertMatchTo(service.getAll(), restaurant_2_RestaurantTo(RESTAURANT_PEARL),
                restaurant_2_RestaurantTo(RESTAURANT_SUPER_STAR),
                restaurant_2_RestaurantTo(RESTAURANT_BLACK_PEARL));
    }

    @Test
     void deleteNotFound()  {
        assertThrows(NotFoundException.class, () ->
        service.delete(1, MANAGER_ID));
    }

    @Test
     void deleteNotOwn() {
        assertThrows(NotFoundException.class, () ->
        service.delete(RESTAURANT_ID, MANAGER_1_ID));
    }

    @Test
    public void update() {
        Restaurant updated = getUpdated();
        service.update(updated, MANAGER_ID);
        assertMatch(service.get(RESTAURANT_ID, MANAGER_ID), updated);
    }

    @Test
    public void updateNotFound() {
        NotFoundException e =  assertThrows(NotFoundException.class, () -> service.update(RESTAURANT_STAR, MANAGER_1_ID));
       // thrown.expectMessage("Not found entity with id=" + RESTAURANT_ID);
        assertEquals(e.getMessage(), "Not found entity with id=" + RESTAURANT_ID);

    }
}